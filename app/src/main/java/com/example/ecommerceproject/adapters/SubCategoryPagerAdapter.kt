package com.example.ecommerceproject.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerceproject.view.AndroidFragment
import com.example.ecommerceproject.view.IPhoneFragment
import com.example.ecommerceproject.view.WindowsFragment

class SubcategoryPagerAdapter(fragment: Fragment, private val subcategories: List<String>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = subcategories.size

    override fun createFragment(position: Int): Fragment {
        return when (subcategories[position]) {
            "Android" -> AndroidFragment()
            "iPhone" -> IPhoneFragment()
            "Windows" -> WindowsFragment()
            else -> AndroidFragment()
        }
    }
}
