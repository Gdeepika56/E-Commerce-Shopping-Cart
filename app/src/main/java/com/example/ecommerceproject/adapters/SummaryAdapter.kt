package com.example.ecommerceproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceproject.data.CartItem
import com.example.ecommerceproject.databinding.ViewHolderSummaryBinding

class SummaryAdapter (val cartItem: List<CartItem>): RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderSummaryBinding.inflate(inflater,parent,false)

        return SummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        holder.bind(cartItem[position])
    }

    override fun getItemCount() = cartItem.size


    inner class SummaryViewHolder(val binding: ViewHolderSummaryBinding): ViewHolder(binding.root) {

        fun bind(cartItem: CartItem){
            with(binding){
                Log.d(SummaryViewHolder::class.simpleName, "$cartItem")
                tvProductName.text = cartItem.productName
                tvProductPrice.text = "Unit Price ${cartItem.unitPrice}"
                tvProductQuantity.text = "Quantity ${cartItem.quantity}"
                tvProductAmount.text = "Amount $ ${cartItem.quantity*cartItem.unitPrice}"

            }

        }
    }


}