package com.example.ecommerceproject.adapters


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.R
import com.example.ecommerceproject.databinding.ViewHolderAndroidBinding
import com.example.ecommerceproject.model.Product
import com.example.ecommerceproject.view.activities.ProductDetailsActivity
import com.example.ecommerceproject.view.fragments.ProductDetailsFragment
import com.squareup.picasso.Picasso

class AndroidAdapter(private var androidList: List<Product>) : RecyclerView.Adapter<AndroidAdapter.AndroidViewHolder>() {

    private var onItemClick: ((productId: String) -> Unit)? = null

    fun setOnItemClickListener(listener: (productId: String) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndroidViewHolder {
        val binding = ViewHolderAndroidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AndroidViewHolder(binding)
    }

    override fun getItemCount(): Int = androidList.size

    override fun onBindViewHolder(holder: AndroidViewHolder, position: Int) {
        val product = androidList[position]
        holder.bind(product)

        holder.binding.productDescription.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("product_id", androidList[position].productid)
            context.startActivity(intent)
        }
    }

    fun updateList(newList: List<Product>) {
        androidList = newList
        notifyDataSetChanged()
    }

    class AndroidViewHolder(val binding: ViewHolderAndroidBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.productname
            binding.productDescription.text = product.description
            binding.productPrice.text = product.price
            binding.productRating.rating = product.averagerating
            Picasso.get().load("https://apolisrises.co.in/myshop/images/${product.productimage_url}").into(binding.productImage)
        }
    }
}
