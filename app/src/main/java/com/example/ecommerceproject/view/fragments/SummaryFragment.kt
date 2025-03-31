package com.example.ecommerceproject.view.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceproject.R
import com.example.ecommerceproject.adapters.SummaryAdapter
import com.example.ecommerceproject.data.AppDatabase
import com.example.ecommerceproject.databinding.FragmentSummaryBinding
import com.example.ecommerceproject.model.Items
import com.example.ecommerceproject.model.OrderAddress
import com.example.ecommerceproject.model.PlaceOrderRequest
import com.example.ecommerceproject.remote.ApiClient
import com.example.ecommerceproject.remote.ApiResult
import com.example.ecommerceproject.repository.LocalRepository
import com.example.ecommerceproject.repository.ProductRepository
import com.example.ecommerceproject.viewmodel.DeliveryVieModelFactory
import com.example.ecommerceproject.viewmodel.DeliveryViewModel
import com.example.ecommerceproject.viewmodel.LocalViewModel
import com.example.ecommerceproject.viewmodel.LocalViewModelFactory
import com.example.ecommerceproject.viewmodel.PlaceOrderViewModel

class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private lateinit var viewModel: DeliveryViewModel
    private lateinit var orderviewModel: PlaceOrderViewModel
    private lateinit var orderItems: List<Items>
    private lateinit var localViewModel: LocalViewModel
    private var totalBillAmount = 0


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(inflater,container,false)

        initViewModels()
        setupObserevers()
        initOrder()

        localViewModel.getAllCartItems()
        return binding.root
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun initOrder() {
        binding.btnConfirmOrder.setOnClickListener {
            val orderAddress = viewModel.selectedShippingAddress.value?.let { it ->
                OrderAddress(it.title, it.address
                )
            }?:OrderAddress("NA", "NA")


            val placeOrder = PlaceOrderRequest(444, orderAddress, orderItems, totalBillAmount.toLong(),
                viewModel.selectedShippingAddress.toString()
            )
            orderviewModel.placeFinalOrder(placeOrder)

        }
    }


    private fun setupObserevers() {
        localViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            val adapter = SummaryAdapter(cartItems)
            binding.rvSummary.adapter = adapter

            totalBillAmount = cartItems.sumOf { it.unitPrice*it.quantity}

            binding.tvTotalPriceCart.text = totalBillAmount.toString()
        }

        viewModel.selectedShippingAddress.observe(viewLifecycleOwner){
            binding.tvAddressTitle.text = it.title
            binding.tvAddress.text = it.address
        }

        viewModel.selectedPaymentMode.observe(viewLifecycleOwner){
            binding.tvPayment.text = it
        }

        orderviewModel.apiStatePlaceOrder.observe(viewLifecycleOwner){
            when(it){
                is ApiResult.Error  -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is ApiResult.Success -> {
                    with(binding){
                        llOrderId.visibility = View.VISIBLE
                        llOrderstatus.visibility = View.VISIBLE
                        tvOrderId.text = "# ${it.data.orderId}"
                        btnConfirmOrder.text = "Cancel Order"
                        btnConfirmOrder.isEnabled = false
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

    private fun initViewModels() {
        val repository = ProductRepository(ApiClient.apiService)
        val factory = DeliveryVieModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory)[DeliveryViewModel::class.java]

        val localRepo =LocalRepository(AppDatabase.getInstance(requireContext()))
        val factoryLocal = LocalViewModelFactory(localRepo)
        localViewModel = ViewModelProvider(this, factoryLocal)[LocalViewModel::class.java]
    }

}