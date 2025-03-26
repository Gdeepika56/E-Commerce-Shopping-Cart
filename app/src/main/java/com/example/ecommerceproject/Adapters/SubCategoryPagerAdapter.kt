package com.example.ecommerceproject.Adapters



import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerceproject.model.Subcategory
import com.example.ecommerceproject.view.AndroidFragment
import com.example.ecommerceproject.view.IPhoneFragment
import com.example.ecommerceproject.view.WindowsFragment


class SubcategoryPagerAdapter(fragment: Fragment, private val subcategories: List<Subcategory>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = subcategories.size

    override fun createFragment(position: Int): Fragment {
        val subcategory = subcategories[position]

        val subcategoryId = subcategory.subcategoryId.toIntOrNull() ?: -1

        return when (subcategory.subcategoryName) {
            "Android" -> AndroidFragment.newInstance(subcategoryId)
            "iPhone" -> IPhoneFragment.newInstance(subcategoryId)
            "Windows" -> WindowsFragment.newInstance(subcategoryId)
            else -> AndroidFragment.newInstance(subcategoryId)
        }
    }
}
