package com.example.trempelapp.presentation_layer.mainScreen.main_fragments.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentProductListBinding
import com.example.trempelapp.utils.CATEGORY_TO_PRODUCT_KEY

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
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setUpObservers()
        setUpBinding()
        settingTitle()
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
        viewModel.productListLiveData.observe(this, {
            viewModel.adapter.updateItems(it)
        })
    }
}
