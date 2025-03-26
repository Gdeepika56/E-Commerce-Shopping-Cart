package com.example.ecommerceproject.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.R
import com.example.ecommerceproject.databinding.ViewHolderAndroidBinding
import com.example.ecommerceproject.model.Product
import com.squareup.picasso.Picasso


class AndroidAdapter(var androidList: List<Product>):RecyclerView.Adapter<AndroidAdapter.AndroidViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndroidViewHolder {
        var binding = ViewHolderAndroidBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AndroidViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return androidList.size
    }

    override fun onBindViewHolder(holder: AndroidViewHolder, position: Int) {
        holder.bind(androidList[position])

        holder.binding.addToCartButton.setOnClickListener(){
            if(::addProductToCart.isInitialized){
                addProductToCart(androidList[position],position)
            }
        }
    }

    private lateinit var addProductToCart:(product:Product,position:Int) -> Unit

    inner class AndroidViewHolder(var binding: ViewHolderAndroidBinding):RecyclerView.ViewHolder(binding.root) {


        fun bind(android:Product){
            with(binding){
                productName.text = "Name : ${android.productname}"
                productDescription.text = android.description
                productPrice.text = "Price : $ ${android.price}"
                productRating.rating = android.averagerating.toFloat()
                Log.d("images","https://apolisrises.co.in/myshop/images/"+android.productimage_url)
                Picasso.get().load("https://apolisrises.co.in/myshop/images/"+android.productimage_url).placeholder(
                    R.drawable.ic_launcher_foreground).into(productImage)
            }
        }
    }


}