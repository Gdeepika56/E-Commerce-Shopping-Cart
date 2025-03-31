package com.example.ecommerceproject.view.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceproject.R
import com.example.ecommerceproject.databinding.ActivityProductDetailsBinding
import com.example.ecommerceproject.model.ProductDetails
import com.example.ecommerceproject.remote.ApiClient
import com.example.ecommerceproject.repository.ProductRepository
import com.example.ecommerceproject.viewmodel.AndroidViewModel
import com.example.ecommerceproject.viewmodel.PhonesViewModelFactory

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var viewModel: AndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("product_id") ?: return

        val repo = ProductRepository(ApiClient.apiService)
        viewModel = ViewModelProvider(this, PhonesViewModelFactory(repo))[AndroidViewModel::class.java]

        viewModel.getProductDetails(productId)

        viewModel.apiProductDetailsResult.observe(this) {
            setProductDetails(it)
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setProductDetails(product: ProductDetails) {
        val p = product.product

        binding.tvProductTitle.text = p.product_name
        binding.tvDescription.text = p.description
        binding.tvPrice.text = "$${p.price}"
        binding.ratingBar.rating = p.average_rating.toFloatOrNull() ?: 0f

//        binding.viewPagerImages.adapter = ProductImagesAdapter(p.images.map { it.image })

        binding.specsContainer.removeAllViews()
        p.specifications.forEach {
            val spec = TextView(this)
            spec.text = "${it.title}: ${it.specification}"
            spec.setPadding(8, 4, 8, 4)
            binding.specsContainer.addView(spec)
        }

    }
}