package com.example.ecommerceproject.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceproject.R
import com.example.ecommerceproject.adapters.CartAdapter
import com.example.ecommerceproject.data.AppDatabase
import com.example.ecommerceproject.databinding.FragmentCartBinding
import com.example.ecommerceproject.repository.LocalRepository
import com.example.ecommerceproject.viewmodel.LocalViewModel
import com.example.ecommerceproject.viewmodel.LocalViewModelFactory

class CartFragment: Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var localViewModel: LocalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        initDb()
        setupObserver()
        localViewModel.getAllCartItems()

        binding.btnCheckout.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.drawer_layout, CheckoutFragment())
                .addToBackStack("CartFragment")
                .commit()
        }
        return binding.root
    }

    private fun setupObserver() {
        localViewModel.cartItems.observe(viewLifecycleOwner) {
            val adapter = CartAdapter(it, this)
            binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerCart.adapter = adapter
        }

        localViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.txtTotalPriceCartItems.text = it.toString()
        }
    }

    private fun initDb() {
        val localRepo = LocalRepository(AppDatabase.getInstance(requireContext()))
        val factoryLocal = LocalViewModelFactory(localRepo)
        localViewModel = ViewModelProvider(this, factoryLocal)[LocalViewModel::class.java]
    }
}
