package com.example.ecommerceproject.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceproject.databinding.FragmentProductdetailsBinding
import com.example.ecommerceproject.model.ProductDetails
import com.example.ecommerceproject.remote.ApiClient
import com.example.ecommerceproject.repository.ProductRepository
import com.example.ecommerceproject.viewmodel.AndroidViewModel
import com.example.ecommerceproject.viewmodel.PhonesViewModelFactory

class ProductDetailsFragment: Fragment() {

    private lateinit var binding: FragmentProductdetailsBinding

    private lateinit var viewModel: AndroidViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductdetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product_Id = arguments?.getString("productId") ?: return

        val repo = ProductRepository(ApiClient.apiService)
        viewModel = ViewModelProvider(requireActivity(),PhonesViewModelFactory(repo))[AndroidViewModel::class.java]

        viewModel.getProductDetails(product_Id)

        viewModel.apiProductDetailsResult.observe(viewLifecycleOwner) {productDetails ->
            setProductdetails(productDetails)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setProductdetails(productDetails: ProductDetails){
        val productData = productDetails.product

        with(binding){
            tvProductName.text = productData.product_name
            tvProductDescription.text = productData.description
            tvPrice.text = "$${productData.price}"
            rtBar.rating = productData.average_rating.toFloatOrNull() ?: 0f
        }

        binding.Container.removeAllViews()
        for(specification in productData.specifications) {
            val textView = TextView(requireContext())
            textView.text = "${specification.title}: ${specification.specification}"
            textView.setPadding(8,4,8,4)
            binding.Container.addView(textView)
        }

    }
}