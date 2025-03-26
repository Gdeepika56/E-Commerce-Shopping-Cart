package com.example.ecommerceproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerceproject.R
import com.example.ecommerceproject.databinding.FragmentIPhoneBinding

class IPhoneFragment : Fragment() {

    private lateinit var binding: FragmentIPhoneBinding

    companion object {
        fun newInstance(subcategoryId: Int): IPhoneFragment {
            val fragment = IPhoneFragment()
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
        binding = FragmentIPhoneBinding.inflate(layoutInflater)
        return binding.root
    }
}