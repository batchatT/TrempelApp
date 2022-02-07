package com.example.trempelapp.presentation_layer.main_screen.main_fragments.favourites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.trempelapp.BR
import com.example.trempelapp.data_layer.models.Favourite
import com.example.trempelapp.databinding.TrempelFavouriteItemBinding
import com.example.trempelapp.utils.SingleLiveEvent

class FavouritesRecyclerAdapter(
    private val changeStatus: MutableLiveData<Favourite>,
    private val favouriteToRemove: SingleLiveEvent<Favourite>
) : RecyclerView.Adapter<FavouritesRecyclerAdapter.ViewHolder>() {

    private lateinit var onFavouriteListener: OnFavouriteListener
    private val favouritesList = mutableListOf<Favourite>()
    private var recentlyRemovedItem: Favourite? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TrempelFavouriteItemBinding.inflate(layoutInflater, parent, false)
        binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return ViewHolder(binding, changeStatus)
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
    fun updateItems(_favouritesList: List<Favourite>) {
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
        private val changeStatus: MutableLiveData<Favourite>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favourite: Favourite, listener: OnFavouriteListener) {
            itemView.setOnClickListener {
                listener.onFavouriteListener(favourite)
            }
            binding.setVariable(BR.favourite, FavouriteRecyclerItem(favourite, changeStatus))
        }
    }

    interface OnFavouriteListener {
        fun onFavouriteListener(favourite: Favourite)
    }
}
