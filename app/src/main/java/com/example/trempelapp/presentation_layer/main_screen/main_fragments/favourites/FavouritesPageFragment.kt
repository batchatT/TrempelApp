package com.example.trempelapp.presentation_layer.main_screen.main_fragments.favourites

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentFavouritesPageBinding
import com.example.trempelapp.utils.PRODUCT_TO_DETAILS_KEY
import com.example.trempelapp.utils.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar

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
        viewModel.favouriteListLiveData.observe(viewLifecycleOwner) {
            viewModel.adapter.updateItems(it)
        }
        viewModel.favouriteToRemoveLiveData.observe(viewLifecycleOwner) {
            viewModel.removeFavouriteItem(it.favourite)
        }
        viewModel.onAddToCartClicked.observe(viewLifecycleOwner) {
            Toast.makeText(context, getString(R.string.item_added_to_the_cart), Toast.LENGTH_SHORT).show()
        }
        viewModel.onProductClickedLiveData.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putInt(PRODUCT_TO_DETAILS_KEY, it.id)
            findNavController().navigate(
                R.id.action_favouritesPageFragment_to_productDetailsPageFragment,
                bundle
            )
        }
    }

    private fun setUpSwipeHandlerForFavouriteRecycler() {
        val swipeHandler = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedItemPosition = viewHolder.bindingAdapterPosition
                viewModel.adapter.removeItem(removedItemPosition)
                showUndoSnackBar(removedItemPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.favouritesRecycler)
    }

    private fun showUndoSnackBar(position: Int) {
        val snackBar = Snackbar.make(
            binding.constraintLayout, R.string.undo_deletion,
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction(R.string.undo_string) {
            viewModel.insertFavourite(position)
        }
        snackBar.setActionTextColor(Color.WHITE)
        snackBar.show()
    }
}
