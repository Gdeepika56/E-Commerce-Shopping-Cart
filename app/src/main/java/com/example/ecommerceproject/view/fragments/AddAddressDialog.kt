package com.example.ecommerceproject.view.fragments

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.ecommerceproject.databinding.DialogAddAddressBinding
import com.example.ecommerceproject.model.DeliveryAddressRequest
import com.example.ecommerceproject.remote.ApiClient
import com.example.ecommerceproject.remote.ApiResult
import com.example.ecommerceproject.repository.ProductRepository
import com.example.ecommerceproject.viewmodel.DeliveryVieModelFactory
import com.example.ecommerceproject.viewmodel.DeliveryViewModel
import com.google.android.material.snackbar.Snackbar


class AddAddressDialog(
    context:Context, private val lifecycleOwner: LifecycleOwner,
    private val viewLifecycleOwner: ViewModelStoreOwner):Dialog(context){

    private lateinit var binding: DialogAddAddressBinding
    private lateinit var viewModel: DeliveryViewModel
    var onSaveAddress: ((String, String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAddAddressBinding.inflate(layoutInflater)
        window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setContentView(binding.root)

        initViewModel()
        setupObserver()
        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val address = binding.etAddress.toString()

            if(title.isEmpty() || address.isEmpty()) {
                showSnackbar("Please fill all fields")
            }else{
                showLoading(true)
                onSaveAddress?.invoke(title,address)
                viewModel.addDeliveryAddress(DeliveryAddressRequest(1, title, address))
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        binding.btnSave.isEnabled = !isLoading
        binding.btnCancel.isEnabled = !isLoading
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupObserver() {
        viewModel.apiStateAddAddress.observe(lifecycleOwner) { state ->
            when(state) {
                is ApiResult.Loading -> showLoading(true)
                is ApiResult.Error -> {
                    showLoading(false)
                    showSnackbar(state.message ?: "error")
                }
                is ApiResult.Success -> {
                    showLoading(false)
                    showSnackbar("Address added Successfully")
                    dismiss()
                }
            }
        }
    }

    private fun initViewModel() {
        val repository = ProductRepository(ApiClient.apiService)
        val factory = DeliveryVieModelFactory(repository)
        viewModel = ViewModelProvider(viewLifecycleOwner, factory)[DeliveryViewModel::class.java]
    }


}