package com.example.trempelapp.presentation_layer.main_screen.main_fragments.favourites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.trempelapp.BR
import com.example.trempelapp.databinding.TrempelFavouriteItemBinding
import com.example.trempelapp.utils.SingleLiveEvent

class FavouritesRecyclerAdapter(
    private val favouriteToRemoveLiveData: SingleLiveEvent<FavouriteRecyclerItem>,
) : RecyclerView.Adapter<FavouritesRecyclerAdapter.ViewHolder>() {

    private lateinit var onFavouriteListener: OnFavouriteListener
    private var favouritesList = listOf<FavouriteRecyclerItem>()
    private var recentlyRemovedItem: FavouriteRecyclerItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TrempelFavouriteItemBinding.inflate(layoutInflater, parent, false)
        binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return ViewHolder(
            binding,
        )
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
    fun updateItems(_favouritesList: List<FavouriteRecyclerItem>) {
        favouritesList = _favouritesList
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        favouriteToRemoveLiveData.value = favouritesList[position]
        recentlyRemovedItem = favouriteToRemoveLiveData.value
        notifyItemRemoved(position)
    }

    class ViewHolder(
        private val binding: TrempelFavouriteItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            favourite: FavouriteRecyclerItem,
            listener: OnFavouriteListener,
        ) {
            itemView.setOnClickListener {
                listener.onFavouriteListener(favourite)
            }
            binding.setVariable(BR.favourite, favourite)
        }
    }

    interface OnFavouriteListener {
        fun onFavouriteListener(favourite: FavouriteRecyclerItem)
    }
}
