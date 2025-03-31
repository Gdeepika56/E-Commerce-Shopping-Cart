package com.example.ecommerceproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.databinding.ViewHolderSpecificationsBinding
import com.example.ecommerceproject.model.Specification

class SpecificationsAdapter(private val specifications:List<Specification>): RecyclerView.Adapter<SpecificationsAdapter.SpecificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationViewHolder {
        val binding =ViewHolderSpecificationsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SpecificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecificationViewHolder, position: Int) {
        holder.bind(specifications[position])
    }

    override fun getItemCount(): Int = specifications.size


    inner class SpecificationViewHolder(val binding: ViewHolderSpecificationsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(specification: Specification) {
            with(binding) {
                tvSpecificationTitle.text = specification.title
                tvSpecifications.text = specification.specification
            }
        }
    }


}