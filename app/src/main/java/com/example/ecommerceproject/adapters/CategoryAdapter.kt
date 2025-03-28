package com.example.ecommerceproject.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceproject.databinding.ViewHolderCategoryBinding
import com.example.ecommerceproject.model.CategoryRequest

class CategoryAdapter(private var categories: List<CategoryRequest>, private val onCategoryClick:(CategoryRequest) ->Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ViewHolderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun updateCategories(newCategories: List<CategoryRequest>){
        categories = newCategories
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(private val binding: ViewHolderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryRequest) {
            binding.tvCategoryName.text = category.category_name
            Glide.with(binding.root.context)
                .load("https://apolisrises.co.in/myshop/images/${category.category_image_url}")
                .into(binding.ivCategoryImage)
            binding.root.setOnClickListener { onCategoryClick(category) }
        }
    }
}
