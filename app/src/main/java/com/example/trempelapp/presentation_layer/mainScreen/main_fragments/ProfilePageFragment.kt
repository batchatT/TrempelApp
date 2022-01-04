package com.example.trempelapp.presentation_layer.mainScreen.main_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentProfilePageBinding

class ProfilePageFragment : BaseFragment() {

    private val binding by lazy {
        FragmentProfilePageBinding.inflate(layoutInflater)
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
