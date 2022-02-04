package com.example.trempelapp.presentation_layer.main_screen.main_fragments.pdp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
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
        arguments?.let { bundle ->
            bundle.getInt(PRODUCT_TO_DETAILS_KEY).takeIf { it != 0 }?.let {
                viewModel.findProductById(it)
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
        viewModel.insertRecently()
        setUpObservers()
        return binding.root
    }

    private fun setUpBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setUpObservers() {
        viewModel.recentlyProductListLiveData.observe(viewLifecycleOwner) {
            viewModel.adapter.updateItems(it)
        }

        viewModel.onProductClickedLiveData.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            viewModel.recentlyProduct?.id?.let { id -> bundle.putInt(PRODUCT_TO_DETAILS_KEY, id) }
            findNavController().navigate(
                R.id.action_productDetailsPageFragment_self,
                bundle
            )
        }
    }
}
