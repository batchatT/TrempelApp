package com.example.trempelapp.presentation_layer.mainScreen.main_fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentHomePageBinding

class HomePageFragment : BaseFragment() {

    companion object {
        fun newInstance(): HomePageFragment {

            val args = Bundle()

            val homeLoginFragment = HomePageFragment()
            homeLoginFragment.arguments = args
            return homeLoginFragment
        }
    }

    override fun configureAppBar() {
        hideAppBar()
    }

    private val binding by lazy {
        FragmentHomePageBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[HomePageViewModel::class.java]
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
        viewModel.moveToCategoriesLiveData.observe(this, {
            findNavController().navigate(R.id.action_homePage_to_categoriesPage)
        })
        return binding.root
    }

    private fun setUpBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
