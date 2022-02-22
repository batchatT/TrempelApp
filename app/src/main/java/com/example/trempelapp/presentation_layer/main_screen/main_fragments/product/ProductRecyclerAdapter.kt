package com.example.trempelapp.presentation_layer.main_screen.main_fragments.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.trempelapp.databinding.TrempelProductItemBinding

class ProductRecyclerAdapter : RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {

    private lateinit var onProductListener: OnProductListener

    private val productsList = mutableListOf<ProductListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TrempelProductItemBinding.inflate(layoutInflater, parent, false)
        binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productsList[position]
        holder.bind(product, onProductListener)
    }

    override fun getItemCount(): Int = productsList.size

    fun setOnProductListener(onProductListener: OnProductListener) {
        this.onProductListener = onProductListener
    }

    fun updateItems(_productsList: List<ProductListItem>) {
        productsList.clear()
        productsList.addAll(_productsList)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: TrempelProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            product: ProductListItem,
            listener: OnProductListener
        ) {
            itemView.setOnClickListener {
                listener.onProductListener(product)
            }
            binding.setVariable(BR.product, product)
        }
    }

    interface OnProductListener {
        fun onProductListener(product: ProductListItem)
    }
}
