package com.example.ecommerceproject.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.AppDatabase
import com.example.ecommerceproject.data.CartItem
import com.example.ecommerceproject.databinding.ViewHolderCartBinding
import com.example.ecommerceproject.repository.LocalRepository
import com.example.ecommerceproject.viewmodel.LocalViewModel
import com.example.ecommerceproject.viewmodel.LocalViewModelFactory
import com.squareup.picasso.Picasso

class CartAdapter(val cartItems: List<CartItem>, private val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private lateinit var localViewModel: LocalViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderCartBinding.inflate(inflater, parent, false)

        val localRepo= LocalRepository(AppDatabase.getInstance(parent.context))
        val factoryLocal = LocalViewModelFactory(localRepo)
        localViewModel = ViewModelProvider(parent.context as ViewModelStoreOwner, factoryLocal)[LocalViewModel::class.java]

        return CartViewHolder(binding)
    }

    override fun getItemCount() = cartItems.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)


        val productID = cartItem.productId

        with(holder.binding) {
            tvAddtocart.setOnClickListener {

                tvAddtocart.visibility = View.GONE
                tvQuantityBlock.visibility = View.VISIBLE
                localViewModel.addCartItem(cartItem)
                localViewModel.increaseQuantity(productID)
                localViewModel.getQuantityOfProduct(productID)

            }
            BtnQuantityIncrease.setOnClickListener {
                localViewModel.increaseQuantity(productID)
                localViewModel.getQuantityOfProduct(productID)
            }
            BtnQuantityDecrease.setOnClickListener {
                localViewModel.decreaseQuantity(productID)
                localViewModel.getQuantityOfProduct(productID)
            }
        }

        localViewModel.getQuantityOfProduct(productID).observe(lifecycleOwner) {
            if (it == 0) {
                holder.binding.tvQuantityBlock.visibility = View.GONE
                holder.binding.tvAddtocart.visibility = View.VISIBLE
            }
            holder.binding.tvQuantityNumber.text = it.toString()
        }

    }

    inner class CartViewHolder(val binding: ViewHolderCartBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem){
            with(binding){
                tvName.text = cartItem.productName
                tvDescription.text = cartItem.description
                tvPrice.text = "$ ${cartItem.unitPrice}"
                tvTotalPrice.text = "$ ${(cartItem.unitPrice*cartItem.quantity)}"
                val url = "https://apolisrises.co.in/myshop/images/${cartItem.imageUrl}"
                Picasso.get().load(url).into(ImageUrl)
            }
        }
    }

}