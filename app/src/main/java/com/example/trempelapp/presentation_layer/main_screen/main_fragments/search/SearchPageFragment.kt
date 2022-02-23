package com.example.trempelapp.presentation_layer.main_screen.main_fragments.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentSearchPageBinding
import com.example.trempelapp.utils.HOME_TO_SEARCH_KEY
import com.example.trempelapp.utils.PRODUCT_TO_DETAILS_KEY

class SearchPageFragment : BaseFragment() {

    companion object {
        fun newInstance(): SearchPageFragment {

            val args = Bundle()

            val searchPageFragment = SearchPageFragment()
            searchPageFragment.arguments = args
            return searchPageFragment
        }
    }

    lateinit var title: String

    val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[SearchPageFragmentViewModel::class.java]
    }

    private val binding by lazy {
        FragmentSearchPageBinding.inflate(layoutInflater)
    }

    override fun injectDagger() {
        (requireActivity().application as TrempelApplication).trempelApp.inject(this)
    }

    override fun handleArguments() {
        super.handleArguments()
        arguments?.let {
            title = it.getString(HOME_TO_SEARCH_KEY).toString()
            viewModel.queryTextLiveData.value = title
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFilteredListOfProducts(title)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setUpBindings()
        setUpObservers()
        return binding.root
    }

    private fun setUpBindings() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.search.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    val searchText = viewModel.queryTextLiveData.value
                    if (!searchText.isNullOrEmpty()) {
                        viewModel.searchProduct(searchText)
                        return@OnEditorActionListener true
                    }
                }
                false
            }
        )
    }

    private fun setUpObservers() {

        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            viewModel.adapter.updateItems(it)
        }

        viewModel.onProductClickedLiveData.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putInt(PRODUCT_TO_DETAILS_KEY, it.id)
            findNavController().navigate(
                R.id.action_searchPageFragment_to_productDetailsPageFragment,
                bundle
            )
        }

        viewModel.queryTextLiveData.observe(viewLifecycleOwner) {
            viewModel.clearQueryError()
        }

        viewModel.favouritesListLiveData.observe(viewLifecycleOwner) {
            viewModel.checkFavourites()
        }

        viewModel.onAddProductToCartLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, getString(R.string.item_added_to_the_cart), Toast.LENGTH_SHORT)
                .show()
        }
    }
}
