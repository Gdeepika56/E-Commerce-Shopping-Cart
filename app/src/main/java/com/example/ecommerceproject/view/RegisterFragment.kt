package com.example.ecommerceproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ecommerceproject.R
import com.example.ecommerceproject.databinding.FragmentRegisterBinding
import com.example.ecommerceproject.repository.AuthRepository
import com.example.ecommerceproject.viewmodel.AuthViewModel
import com.example.ecommerceproject.viewmodel.AuthViewModelFactory

class RegisterFragment: Fragment() {
    lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by viewModels { AuthViewModelFactory(AuthRepository())  }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            with(binding){
                val fullName = etFullname.text.toString()
                val mobile = etMobileno.text.toString()
                val email = etEmail.toString()
                val password =  etPassword.toString()

                if(fullName.isEmpty() || mobile.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
                }else{
                    authViewModel.registerUser(fullName,mobile,email,password)
                }
            }
        }

        authViewModel.registerResponse.observe(viewLifecycleOwner) {response ->
            if(response.isSuccessful && response.body()?.status==0) {
                Toast.makeText(requireContext(), "Registration Successful!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Registration Falied", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvAccount.setOnClickListener{
            parentFragmentManager.beginTransaction()
//                .hide(this)
                .replace(R.id.fragment_container, LoginFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}