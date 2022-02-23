package com.example.trempelapp.presentation_layer.main_screen.main_fragments.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.trempelapp.BR
import com.example.trempelapp.databinding.TrempelCartItemBinding
import com.example.utils.SingleLiveEvent
import kotlinx.android.synthetic.main.trempel_cart_item.view.view

class CartItemRecyclerAdapter(
    private val cartItemToRemoveLiveData: SingleLiveEvent<CartRecyclerItem>,
) : RecyclerView.Adapter<CartItemRecyclerAdapter.ViewHolder>() {

    private lateinit var onCartItemListener: OnCartItemListener
    private val cartItemsList = mutableListOf<CartRecyclerItem>()
    private var recentlyRemovedItem: CartRecyclerItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TrempelCartItemBinding.inflate(layoutInflater, parent, false)
        binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItemsList[position]
        holder.bind(cartItem, onCartItemListener)
    }

    override fun getItemCount(): Int = cartItemsList.size

    fun setOnProductListener(onCartItemListener: OnCartItemListener) {
        this.onCartItemListener = onCartItemListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(_cartItemsList: List<CartRecyclerItem>) {
        cartItemsList.clear()
        cartItemsList.addAll(_cartItemsList)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        cartItemToRemoveLiveData.value = cartItemsList[position]
        recentlyRemovedItem = cartItemToRemoveLiveData.value
        notifyItemRemoved(position)
    }

    class ViewHolder(
        private val binding: TrempelCartItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            cartItem: CartRecyclerItem,
            listener: OnCartItemListener,
        ) {
            itemView.view.setOnClickListener {
                listener.onCartItemListener(cartItem)
            }
            binding.setVariable(BR.product, cartItem)
        }
    }

    interface OnCartItemListener {
        fun onCartItemListener(cartItem: CartRecyclerItem)
    }
}
