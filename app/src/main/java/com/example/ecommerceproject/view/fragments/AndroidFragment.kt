package com.example.ecommerceproject.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceproject.R
import com.example.ecommerceproject.adapters.AndroidAdapter
import com.example.ecommerceproject.databinding.FragmentAndroidBinding
import com.example.ecommerceproject.model.Product
import com.example.ecommerceproject.remote.ApiClient
import com.example.ecommerceproject.viewmodel.AndroidViewModel
import com.example.ecommerceproject.repository.ProductRepository
import com.example.ecommerceproject.remote.ApiResult
import com.example.ecommerceproject.viewmodel.PhonesViewModelFactory

class AndroidFragment : Fragment() {

    private var _binding: FragmentAndroidBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AndroidAdapter
    private val viewModel: AndroidViewModel by viewModels { PhonesViewModelFactory(ProductRepository(
        ApiClient.apiService))}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAndroidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val subCategoryId = 1
        fetchProducts(subCategoryId)
    }

    private fun setupRecyclerView() {
        binding.rvAndroid.layoutManager = LinearLayoutManager(requireContext())
        adapter = AndroidAdapter(ArrayList())
        binding.rvAndroid.adapter = adapter

        adapter.setOnItemClickListener { productId ->
            val fragment = ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString("productId", productId)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.Container2, fragment)
                .addToBackStack(null)
                .commit()
        }
    }


    private fun fetchProducts(subCategoryId: Int) {
        viewModel.androidAndroidList(subCategoryId)

        viewModel.apiAndroidResult.observe(viewLifecycleOwner) { result ->
            when(result) {
                is ApiResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ApiResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val productList: List<Product> = result.data.products
                    adapter.updateList(productList)
                }

                is ApiResult.Error -> {
                   binding.progressBar.visibility = View.GONE
                   Toast.makeText(context,result.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
