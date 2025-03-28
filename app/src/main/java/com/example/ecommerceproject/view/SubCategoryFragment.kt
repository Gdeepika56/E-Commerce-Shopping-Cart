package com.example.ecommerceproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerceproject.adapters.SubcategoryPagerAdapter
import com.example.ecommerceproject.databinding.FragmentSubcategoryBinding
import com.google.android.material.tabs.TabLayoutMediator

class SubcategoryFragment : Fragment() {

    private lateinit var binding: FragmentSubcategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubcategoryBinding.inflate(inflater, container, false)

        val subcategories = listOf("Android", "iPhone", "Windows")
        binding.viewPager.adapter = SubcategoryPagerAdapter(this, subcategories)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = subcategories[position]
        }.attach()

        return binding.root
    }
}