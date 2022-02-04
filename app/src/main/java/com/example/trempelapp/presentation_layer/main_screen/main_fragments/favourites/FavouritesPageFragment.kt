package com.example.trempelapp.presentation_layer.main_screen.main_fragments.favourites

import SwipeToDeleteCallback
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[FavouritesPageViewModel::class.java]
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
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setUpBindings()
        viewModel.getAllFavourites()
        setUpSwipeHandlerForFavouriteRecycler()
        setUpObservers()
        return binding.root
    }

    private fun setUpBindings() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setUpObservers() {
        viewModel.favouriteDBListLiveData.observe(viewLifecycleOwner) {
            viewModel.adapter.updateItems(it)
        }
        viewModel.changeStatusFavouriteLiveData.observe(viewLifecycleOwner) {
            viewModel.updateCartButton()
        }
        viewModel.favouriteToRemove.observe(viewLifecycleOwner) {
            viewModel.removeFavouriteItem(it)
        }
    }

    private fun setUpSwipeHandlerForFavouriteRecycler() {
        val swipeHandler = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.adapter.removeItem(viewHolder.bindingAdapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.favouritesRecycler)
    }
}
