package com.example.ecommerceproject.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerceproject.databinding.FragmentWindowsBinding

class WindowsFragment : Fragment() {

    private lateinit var binding: FragmentWindowsBinding

    companion object {
        fun newInstance(subcategoryId: Int): WindowsFragment {
            val fragment = WindowsFragment()
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
        binding = FragmentWindowsBinding.inflate(layoutInflater)
        return binding.root

    }

}