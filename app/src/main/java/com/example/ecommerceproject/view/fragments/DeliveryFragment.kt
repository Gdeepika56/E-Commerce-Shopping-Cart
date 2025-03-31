package com.example.ecommerceproject.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceproject.R
import com.example.ecommerceproject.adapters.DeliveryAdapter
import com.example.ecommerceproject.databinding.FragmentDeliveryBinding
import com.example.ecommerceproject.model.Address
import com.example.ecommerceproject.model.DeliveryAddressRequest
import com.example.ecommerceproject.model.Product
import com.example.ecommerceproject.remote.ApiClient
import com.example.ecommerceproject.remote.ApiResult
import com.example.ecommerceproject.repository.ProductRepository
import com.example.ecommerceproject.viewmodel.DeliveryVieModelFactory
import com.example.ecommerceproject.viewmodel.DeliveryViewModel

class DeliveryFragment : Fragment() {

    private lateinit var viewModel: DeliveryViewModel

    private lateinit var binding: FragmentDeliveryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryBinding.inflate(inflater,container,false)

        initViewModel()
        setupObservers()
        initview()

        return binding.root
    }

    private fun initview() {
        with(binding) {
            btnAddAddress.setOnClickListener {
                AddAddressDialog(requireContext(), viewLifecycleOwner, this@DeliveryFragment).apply {
                    show()
                    onSaveAddress = { title, address ->
                        Log.d(DeliveryFragment::class.simpleName, "Address Added: $title, $address")
                        viewModel.addDeliveryAddress(DeliveryAddressRequest(1, title, address))
                    }
                }
            }
        }

    }

    private fun setupObservers() {

        viewModel.apiStateAddAddress.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResult.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Success -> {
                    Toast.makeText(context, it.data.message, Toast.LENGTH_SHORT).show()
                    viewModel.getAddress(1)
                }
                is ApiResult.Loading -> {
                    Toast.makeText(context,"Loading..", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context,"Unexpected error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.apiStateGetAddress.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResult.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Success -> {
                    val adapter = DeliveryAdapter(it.data.addresses)
                    binding.rvAddress.adapter = adapter
                    adapter.onClickSelectedAddress{ address, i ->
                        viewModel.selectedShippingAddress.value = address
                    }
                }
                is ApiResult.Loading -> {
                    Toast.makeText(context,"Loading..", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context,"Unexpected error", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun initViewModel() {
        val repository = ProductRepository(ApiClient.apiService)
        val factory = DeliveryVieModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory)[DeliveryViewModel::class.java]
    }


}