package com.example.ecommerceproject.view

import com.example.ecommerceproject.Adapters.SubcategoryPagerAdapter
import com.example.ecommerceproject.databinding.FragmentSubcategoryBinding
import com.example.ecommerceproject.repository.SubcategoryRepository
import com.example.ecommerceproject.viewmodel.SubcategoryViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerceproject.model.Subcategory
import com.google.android.material.tabs.TabLayoutMediator

class SubcategoryFragment : Fragment() {

    private lateinit var binding: FragmentSubcategoryBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var subcategoryPagerAdapter: SubcategoryPagerAdapter
    private val subcategoryViewModel: SubcategoryViewModel by activityViewModels {
        SubcategoryViewModel.SubcategoryViewModelFactory(SubcategoryRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubcategoryBinding.inflate(inflater, container, false)

        val categoryId = arguments?.getInt("category_id", -1) ?: -1
        val categoryName = arguments?.getString("category_name") ?: "Subcategories"

        binding.titleText.text = categoryName
        viewPager = binding.viewPager

        subcategoryViewModel.fetchSubcategories(categoryId)

        binding.backArrow.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        subcategoryViewModel.subcategories.observe(viewLifecycleOwner) { subcategories ->
            if (subcategories != null) {
                setupViewPager(subcategories)
            }
        }

        subcategoryViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewPager(subcategories: List<Subcategory>) {

        subcategoryPagerAdapter = SubcategoryPagerAdapter(this, subcategories)

        binding.viewPager.adapter = subcategoryPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Android"
                1 -> "iPhone"
                2 -> "Windows"
                else -> ""
            }
        }.attach()
    }
}
