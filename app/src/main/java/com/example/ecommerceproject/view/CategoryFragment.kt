package com.example.ecommerceproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceproject.Adapters.CategoryAdapter
import com.example.ecommerceproject.databinding.FragmentCategoryBinding
import com.example.ecommerceproject.viewmodel.CategoryViewModel

class CategoryFragment: Fragment() {
    lateinit var binding: FragmentCategoryBinding
    lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View{
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        categoryAdapter = CategoryAdapter(emptyList())
        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCategories.adapter = categoryAdapter


        observeViewModel()
        viewModel.fetchCategories()

    }

    private fun observeViewModel() {
       viewModel.categories.observe(viewLifecycleOwner) { response ->
           response?.categories?.let {
               categoryAdapter.updateCategories(it)
               binding.rvCategories.visibility = View.VISIBLE
           }
       }

       viewModel.error.observe(viewLifecycleOwner){errorMessage ->
           Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
       }
    }

}