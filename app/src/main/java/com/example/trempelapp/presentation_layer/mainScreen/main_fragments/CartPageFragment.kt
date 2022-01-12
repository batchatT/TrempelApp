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

    companion object {
        fun newInstance(): CartPageFragment {

            val args = Bundle()

            val cartLoginFragment = CartPageFragment()
            cartLoginFragment.arguments = args
            return cartLoginFragment
        }
    }

    private val binding by lazy {
        FragmentCartPageBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[CartPageViewModel::class.java]
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
        return binding.root
    }
}
