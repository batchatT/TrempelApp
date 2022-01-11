package com.example.trempelapp.presentation_layer.mainScreen.main_fragments.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentCategoriesPageBinding
import com.example.trempelapp.utils.CATEGORY_TO_PRODUCT_KEY

class CategoriesPageFragment : BaseFragment() {

    companion object {
        fun newInstance(): CategoriesPageFragment {

            val args = Bundle()

            val categoriesLoginFragment = CategoriesPageFragment()
            categoriesLoginFragment.arguments = args
            return categoriesLoginFragment
        }
    }

    override fun configureAppBar() {
        showAppBar()
        changeAppBarTitle(getString(R.string.categories))
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[CategoriesPageViewModel::class.java]
    }

    private val binding by lazy {
        FragmentCategoriesPageBinding.inflate(layoutInflater)
    }

    override fun injectDagger() {
        (requireActivity().application as TrempelApplication).trempelApp.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setUpBinding()
        setUpObservers()

        return binding.root
    }
    private fun setUpBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setUpObservers() {
        viewModel.onCategoryClickedLiveData.observe(this, {
            val bundle = Bundle()
            bundle.putString(CATEGORY_TO_PRODUCT_KEY, viewModel.categoryTitleLiveData.value)
            findNavController().navigate(R.id.action_categoriesPage_to_productListFragment, bundle)
        })

        viewModel.categoryListLiveData.observe(this, {
            viewModel.adapter.updateItems(it)
        })
    }
}
