package com.example.ecommerceproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceproject.Adapters.AndroidAdapter
import com.example.ecommerceproject.databinding.FragmentAndroidBinding
import com.example.ecommerceproject.model.Product


class AndroidFragment : Fragment() {

    private lateinit var binding: FragmentAndroidBinding
    private var androidList: ArrayList<Product>? = null
    var categoryDataListener: CategoryDataListener? = null
    private lateinit var adapter: AndroidAdapter

    companion object {
        fun newInstance(subcategoryId: Int): AndroidFragment {
            val fragment = AndroidFragment()
            val args = Bundle()
            args.putInt("subcategoryId", subcategoryId)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAndroidBinding.inflate(layoutInflater)

        setupRecyclerView()
        androidList = categoryDataListener?.getAndroidList()
        androidList?.let { updateData(it) }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvAndroid.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvAndroid.adapter = AndroidAdapter(ArrayList())
    }

    fun updateData(newList: ArrayList<Product>) {
        androidList = newList
        adapter = AndroidAdapter(newList)
        binding.rvAndroid.adapter = adapter
    }

    interface CategoryDataListener {
        fun getAndroidList(): ArrayList<Product>?
    }
}