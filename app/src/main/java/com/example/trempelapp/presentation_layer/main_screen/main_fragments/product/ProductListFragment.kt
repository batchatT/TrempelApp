package com.example.trempelapp.presentation_layer.main_screen.main_fragments.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentProductListBinding
import com.example.trempelapp.utils.CATEGORY_TO_PRODUCT_KEY
import com.example.trempelapp.utils.PRODUCT_TO_DETAILS_KEY

private const val TAG = "ProductListFragment"

class ProductListFragment : BaseFragment() {

    companion object {
        fun newInstance(): ProductListFragment {

            val args = Bundle()

            val productListFragment = ProductListFragment()
            productListFragment.arguments = args
            return productListFragment
        }
    }

    lateinit var title: String

    private val binding by lazy {
        FragmentProductListBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[ProductListViewModel::class.java]
    }

    override fun handleArguments() {
        super.handleArguments()
        arguments?.let {
            title = it.getString(CATEGORY_TO_PRODUCT_KEY).toString()
        }
    }

    override fun injectDagger() {
        (requireActivity().application as TrempelApplication).trempelApp.inject(this)
    }

    override fun configureAppBar() {
        changeAppBarTitle(
            title.replaceFirstChar {
                it.uppercaseChar()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setUpObservers()
        setUpBinding()
        return binding.root
    }

    private fun setUpBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun settingTitle() {
        title.let {
            viewModel.fetchProductsByCategory(it)
        }
    }

    private fun setUpObservers() {
        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            viewModel.adapter.updateItems(it)
        }

        viewModel.onProductClickedLiveData.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putInt(PRODUCT_TO_DETAILS_KEY, viewModel.productItem.id)
            findNavController().navigate(
                R.id.action_productListFragment_to_productDetailsPageFragment,
                bundle
            )
        }
    }

    override fun onResume() {
        super.onResume()
        settingTitle()
    }
}
