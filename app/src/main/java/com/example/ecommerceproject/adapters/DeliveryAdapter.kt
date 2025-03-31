package com.example.ecommerceproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ecommerceproject.databinding.ViewHolderDeliveryBinding
import com.example.ecommerceproject.model.Address
import com.example.ecommerceproject.model.DeliveryAddressRequest

class DeliveryAdapter(val address: List<Address>): RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {
    private lateinit var binding: ViewHolderDeliveryBinding
    private var selectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
       val inflater =  LayoutInflater.from(parent.context)

       binding = ViewHolderDeliveryBinding.inflate(inflater,parent,false)

       return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val address = address[position]
        holder.bind(address)

        holder.binding.radioButton.isChecked = (position == selectedPosition)

        holder.itemView.setOnClickListener { onAddressSelected(position, address) }
    }

    private fun onAddressSelected(position: Int, address: Address) {
        if (position != selectedPosition) {
            val previousPosition = selectedPosition
            selectedPosition = position

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }

        onSelectAddress?.invoke(address, position)
    }

    private var onSelectAddress: ((Address, Int) -> Unit)? = null

    fun onClickSelectedAddress(listener: (Address, Int) -> Unit) {
        onSelectAddress = listener
    }


    override fun getItemCount() = address.size

    inner class DeliveryViewHolder(val binding: ViewHolderDeliveryBinding): ViewHolder(binding.root) {

        fun bind(address: Address){
            with(binding){
                tvAddressTitle.text = address.title
                tvAddress.text = address.address
            }
        }

    }
}