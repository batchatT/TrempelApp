package com.example.trempelapp.presentation_layer.main_screen.main_fragments.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.databinding.TrempelProductItemBinding

class ProductRecyclerAdapter : RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {

    private lateinit var onProductListener: OnProductListener

    private var productsList = emptyList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TrempelProductItemBinding.inflate(layoutInflater, parent, false)
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

    fun updateItems(_productsList: List<Product>) {
        productsList = _productsList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: TrempelProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, listener: OnProductListener) {
            itemView.setOnClickListener {
                listener.onCategoryListener(product)
            }
            binding.product = product
        }
    }

    interface OnProductListener {
        fun onCategoryListener(product: Product)
    }
}
