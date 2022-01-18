package com.example.trempelapp.presentation_layer.mainScreen.main_fragments.pdp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.databinding.FragmentProductDetailsPageBinding
import com.example.trempelapp.utils.PRODUCT_TO_DETAILS_KEY

class ProductDetailsPageFragment : BaseFragment() {

    companion object {
        fun newInstance(): ProductDetailsPageFragment {

            val args = Bundle()

            val productDetailsPage = ProductDetailsPageFragment()
            productDetailsPage.arguments = args
            return productDetailsPage
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[ProductDetailsPageViewModel::class.java]
    }

    private val binding by lazy {
        FragmentProductDetailsPageBinding.inflate(layoutInflater)
    }

    override fun handleArguments() {
        super.handleArguments()
        arguments?.let {
            it.getParcelable<Product>(PRODUCT_TO_DETAILS_KEY)?.let {
                viewModel.product = it
            }
        }
    }

    override fun injectDagger() {
        (requireActivity().application as TrempelApplication).trempelApp.inject(this)
    }

    override fun configureAppBar() {
        hideAppBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setUpBinding()
        return binding.root
    }

    private fun setUpBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
