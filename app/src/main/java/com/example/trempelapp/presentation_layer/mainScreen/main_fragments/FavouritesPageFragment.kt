package com.example.trempelapp.presentation_layer.mainScreen.main_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentFavouritesPageBinding

class FavouritesPageFragment : BaseFragment() {

    companion object {
        fun newInstance(): FavouritesPageFragment {

            val args = Bundle()

            val favouritesLoginFragment = FavouritesPageFragment()
            favouritesLoginFragment.arguments = args
            return favouritesLoginFragment
        }
    }

    private val binding by lazy {
        FragmentFavouritesPageBinding.inflate(layoutInflater)
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
