package com.example.ecommerceproject.adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerceproject.view.fragments.AndroidFragment
import com.example.ecommerceproject.view.fragments.IPhoneFragment
import com.example.ecommerceproject.view.fragments.WindowsFragment

class SubcategoryPagerAdapter(private val fragments: List<Fragment>, fragmentManager: FragmentManager, lifecycle:Lifecycle) :
    FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}
