package com.example.ecommerceproject.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceproject.R
import com.example.ecommerceproject.databinding.FragmentPaymentBinding
import com.example.ecommerceproject.remote.ApiClient
import com.example.ecommerceproject.repository.ProductRepository
import com.example.ecommerceproject.viewmodel.DeliveryVieModelFactory
import com.example.ecommerceproject.viewmodel.DeliveryViewModel

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: DeliveryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater,container,false)

        initView()
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    private fun initView() {
        val repository = ProductRepository(ApiClient.apiService)
        val factory = DeliveryVieModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(),factory)[DeliveryViewModel::class.java]

        binding.btnNextpay.setOnClickListener {
            val selectedPayment = when {
                binding.rbCOD.isChecked -> "Cash on Delivery"
                binding.rbInternet.isChecked -> "Internet Banking"
                binding.rbCard.isChecked -> "Debite/Credit Card"
                binding.rbPayPal.isChecked -> "PayPal"
                else -> null
            }

            if(selectedPayment != null) {
                viewModel.selectedPaymentMode.value = selectedPayment
            }else{
                Toast.makeText(requireContext(), "Please select a payment method", Toast.LENGTH_SHORT).show()
            }
        }
    }
}