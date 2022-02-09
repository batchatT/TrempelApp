package com.example.trempelapp.presentation_layer.main_screen.main_fragments.favourites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.trempelapp.BR
import com.example.trempelapp.data_layer.models.Product
import com.example.trempelapp.databinding.TrempelFavouriteItemBinding
import com.example.trempelapp.utils.SingleLiveEvent

class FavouritesRecyclerAdapter(
    private val changeStatusLiveData: MutableLiveData<Product>,
    private val favouriteToRemove: SingleLiveEvent<Product>
) : RecyclerView.Adapter<FavouritesRecyclerAdapter.ViewHolder>() {

    private lateinit var onFavouriteListener: OnFavouriteListener
    private val favouritesList = mutableListOf<Product>()
    private var recentlyRemovedItem: Product? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TrempelFavouriteItemBinding.inflate(layoutInflater, parent, false)
        binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return ViewHolder(binding, changeStatusLiveData)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favouriteItem = favouritesList[position]
        holder.bind(favouriteItem, onFavouriteListener)
    }

    override fun getItemCount(): Int = favouritesList.size

    fun setOnProductListener(onFavouriteListener: OnFavouriteListener) {
        this.onFavouriteListener = onFavouriteListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(_favouritesList: List<Product>) {
        favouritesList.clear()
        favouritesList.addAll(_favouritesList)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        favouriteToRemove.value = favouritesList[position]
        recentlyRemovedItem = favouriteToRemove.value
        favouritesList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun insertItem(position: Int) {
        recentlyRemovedItem?.let {
            favouritesList.add(position, it)
        }
        notifyItemInserted(position)
    }

    class ViewHolder(
        private val binding: TrempelFavouriteItemBinding,
        private val changeStatusLiveData: MutableLiveData<Product>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favourite: Product, listener: OnFavouriteListener) {
            itemView.setOnClickListener {
                listener.onFavouriteListener(favourite)
            }
            binding.setVariable(BR.favourite, FavouriteRecyclerItem(favourite, changeStatusLiveData))
        }
    }

    interface OnFavouriteListener {
        fun onFavouriteListener(favourite: Product)
    }
}
