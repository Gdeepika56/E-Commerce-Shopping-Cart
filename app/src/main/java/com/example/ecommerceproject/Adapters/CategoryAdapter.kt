package com.example.ecommerceproject.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceproject.databinding.ViewHolderCategoryBinding
import com.example.ecommerceproject.model.CategoryRequest

class CategoryAdapter(private val categories: List<CategoryRequest>) :
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

    inner class CategoryViewHolder(private val binding: ViewHolderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryRequest) {
            binding.tvCategoryName.text = category.category_name
            Glide.with(binding.root.context)
                .load("https://apolisrises.co.in/myshop/images/${category.category_image_url}")
                .into(binding.ivCategoryImage)
        }
    }
}
