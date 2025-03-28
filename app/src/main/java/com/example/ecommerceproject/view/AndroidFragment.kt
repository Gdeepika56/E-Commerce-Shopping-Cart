package com.example.ecommerceproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceproject.adapters.AndroidAdapter
import com.example.ecommerceproject.databinding.FragmentAndroidBinding
import com.example.ecommerceproject.viewmodel.AndroidViewModel
import com.example.ecommerceproject.repository.ProductRepository
import com.example.ecommerceproject.remote.ApiResult
import com.example.ecommerceproject.viewmodel.PhonesViewModelFactory

class AndroidFragment : Fragment() {

    private lateinit var binding: FragmentAndroidBinding
    private lateinit var adapter: AndroidAdapter
    private val viewModel: AndroidViewModel by viewModels { PhonesViewModelFactory(ProductRepository()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAndroidBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeData()

        val subCategoryId = arguments?.getInt("subcategoryId") ?: -1
        if (subCategoryId != -1) {
            viewModel.androidAndroidList(subCategoryId)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvAndroid.layoutManager = LinearLayoutManager(requireContext())
        adapter = AndroidAdapter(ArrayList())
        binding.rvAndroid.adapter = adapter
    }

    private fun observeData() {
        viewModel.apiAndroidResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiResult.Success -> adapter.updateList(result.data.products)
                is ApiResult.Error -> {}
                is ApiResult.Loading -> {}
            }
        }
    }
}