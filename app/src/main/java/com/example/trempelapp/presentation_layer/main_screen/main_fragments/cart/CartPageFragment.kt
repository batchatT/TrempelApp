package com.example.trempelapp.presentation_layer.main_screen.main_fragments.cart

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentCartPageBinding
import com.example.trempelapp.utils.PRODUCT_TO_DETAILS_KEY
import com.example.trempelapp.utils.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar

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
        ViewModelProvider(this, viewModelProviderFactory)[CartPageViewModelController::class.java]
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
        seUpBinding()
        setUpObservers()
        setUpSwipeHandlerForRecycler()
        viewModel.getAllProductsFromCart()
        return binding.root
    }

    private fun seUpBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setUpObservers() {
        viewModel.productsListLiveData.observe(viewLifecycleOwner) {
            viewModel.adapter.updateItems(it)
        }
        viewModel.cartItemToRemoveLiveData.observe(viewLifecycleOwner) {
            viewModel.removeCartItemFromCartDB(it.cartItem)
        }
        viewModel.onProductClickedLiveData.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putInt(PRODUCT_TO_DETAILS_KEY, it.id)
            findNavController().navigate(
                R.id.action_cartPageFragment_to_productDetailsPageFragment,
                bundle
            )
        }
    }

    private fun setUpSwipeHandlerForRecycler() {
        val swipeHandler = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedItemPosition = viewHolder.bindingAdapterPosition
                viewModel.adapter.removeItem(removedItemPosition)
                showUndoSnackBar(removedItemPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.cartRecycler)
    }

    private fun showUndoSnackBar(position: Int) {
        val snackBar = Snackbar.make(
            binding.constraintLayout, R.string.undo_deletion,
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction(R.string.undo_string) {
            viewModel.insertProductToCartDB(position)
        }
        snackBar.setActionTextColor(Color.WHITE)
        snackBar.show()
    }
}
