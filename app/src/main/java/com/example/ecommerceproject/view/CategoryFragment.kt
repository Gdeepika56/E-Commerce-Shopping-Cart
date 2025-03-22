package com.example.ecommerceproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceproject.Adapters.CategoryAdapter
import com.example.ecommerceproject.model.remote.ApiClient
import com.example.ecommerceproject.model.remote.ApiService
import com.example.ecommerceproject.databinding.FragmentCategoryBinding
import com.example.ecommerceproject.model.CategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment: Fragment() {
    lateinit var binding: FragmentCategoryBinding
    lateinit var categoryAdapter: CategoryAdapter

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

        fetchCategories()
    }

    private fun fetchCategories() {
        val apiService: ApiService = ApiClient.retrofit.create(ApiService::class.java)
        val call = apiService.getCategories()

        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()?.categories
                    if (categories != null && categories.isNotEmpty()) {
                        categoryAdapter = CategoryAdapter(categories)
                        binding.rvCategories.adapter = categoryAdapter
                        binding.rvCategories.visibility = View.VISIBLE
                    }else{
                        Toast.makeText(requireContext(), "No categories found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load categories", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(requireContext(), "Network Error!", Toast.LENGTH_SHORT).show()
            }
        })
    }


}