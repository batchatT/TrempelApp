package com.example.trempelapp.presentation_layer.mainScreen.main_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentCartPageBinding

class CartPageFragment : BaseFragment() {

    private val binding by lazy {
        FragmentCartPageBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[CartPageViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as TrempelApplication).trempelApp.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}
