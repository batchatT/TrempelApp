package com.example.trempelapp.presentation_layer.main_screen.main_fragments.pdp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trempelapp.BR
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.databinding.TrempelRecentlyProductItemBinding

class RecentlyProductRecyclerAdapter : RecyclerView.Adapter<RecentlyProductRecyclerAdapter.ViewHolder>() {

    private lateinit var onProductListener: OnProductListener

    private val productsList = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TrempelRecentlyProductItemBinding.inflate(layoutInflater, parent, false)
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
        productsList.clear()
        productsList.addAll(_productsList)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: TrempelRecentlyProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, listener: OnProductListener) {
            itemView.setOnClickListener {
                listener.onProductListener(product)
            }
            binding.setVariable(BR.recentlyProduct, product)
        }
    }

    interface OnProductListener {
        fun onProductListener(product: Product)
    }
}
