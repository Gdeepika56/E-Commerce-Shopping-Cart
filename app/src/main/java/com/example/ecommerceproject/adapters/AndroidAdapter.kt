package com.example.ecommerceproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.databinding.ViewHolderAndroidBinding
import com.example.ecommerceproject.model.Product
import com.squareup.picasso.Picasso

class AndroidAdapter(private var androidList: List<Product>) : RecyclerView.Adapter<AndroidAdapter.AndroidViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndroidViewHolder {
        val binding = ViewHolderAndroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AndroidViewHolder(binding)
    }

    override fun getItemCount(): Int = androidList.size

    override fun onBindViewHolder(holder: AndroidViewHolder, position: Int) {
        holder.bind(androidList[position])
    }

    fun updateList(newList: List<Product>) {
        androidList = newList
        notifyDataSetChanged()
    }

    class AndroidViewHolder(private val binding: ViewHolderAndroidBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = "Name: ${product.productname}"
            binding.productDescription.text = product.description
            binding.productPrice.text = "Price: $${product.price}"
            binding.productRating.rating = product.averagerating
            Picasso.get().load("https://apolisrises.co.in/myshop/images/${product.productimage_url}").into(binding.productImage)
        }
    }
}
