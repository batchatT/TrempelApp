package com.example.trempelapp.presentation_layer.mainScreen.home_page_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentHomePageBinding
import com.example.trempelapp.presentation_layer.mainScreen.HomePageViewModelFactory

class HomePageFragment : BaseFragment() {

    private val binding by lazy {
        FragmentHomePageBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, HomePageViewModelFactory())[HomePageFragmentViewModel::class.java]
    }

    override fun injectDagger() {
        viewModel.injectDagger(requireActivity().application as TrempelApplication)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}
