package com.example.trempelapp.presentation_layer.mainScreen.main_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentCategoriesPageBinding

class CategoriesPageFragment : BaseFragment() {

    companion object {
        fun newInstance(): CategoriesPageFragment {

            val args = Bundle()

            val categoriesLoginFragment = CategoriesPageFragment()
            categoriesLoginFragment.arguments = args
            return categoriesLoginFragment
        }
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
        return binding.root
    }
}
