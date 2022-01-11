package com.example.trempelapp.presentation_layer.mainScreen.main_fragments.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trempelapp.BR
import com.example.trempelapp.databinding.TrempelRecyclerItemBinding
import com.example.trempelapp.domain_layer.Category

class CategoriesRecyclerAdapter : RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder>() {

    private lateinit var onCategoryListener: OnCategoryListener

    private var categoriesList = emptyList<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TrempelRecyclerItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoriesList[position], onCategoryListener)
    }

    override fun getItemCount(): Int = categoriesList.size

    fun setOnCategoryListener(onCategoryListener: OnCategoryListener) {
        this.onCategoryListener = onCategoryListener
    }

    fun updateItems(_categoriesList: List<Category>) {
        categoriesList = _categoriesList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: TrempelRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, listener: OnCategoryListener) {
            itemView.setOnClickListener {
                listener.onCategoryListener(category)
            }
            binding.setVariable(BR.category, category)
        }
    }

    interface OnCategoryListener {
        fun onCategoryListener(category: Category)
    }
}
