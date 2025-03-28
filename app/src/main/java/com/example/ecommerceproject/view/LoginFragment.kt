package com.example.ecommerceproject.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceproject.R
import com.example.ecommerceproject.databinding.FragmentLoginBinding
import com.example.ecommerceproject.repository.AuthRepository
import com.example.ecommerceproject.viewmodel.AuthViewModel
import com.example.ecommerceproject.viewmodel.AuthViewModelFactory


class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by activityViewModels { AuthViewModelFactory(AuthRepository()) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            with(binding){
                val email = etEmailId.text.toString()
                val password = etPassword.text.toString()

                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
                }else{
                    authViewModel.loginUser(email,password)
                }

                binding.etEmailId.text.clear()
                binding.etPassword.text.clear()
            }
        }

        authViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            Handler(Looper.getMainLooper()).post {
                if (response.isSuccessful && response.body()?.status == 0) {
                    Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()
                    if (isAdded) {
                        parentFragmentManager.beginTransaction()
                            .hide(this@LoginFragment)
                            .replace(R.id.fragment_container, CategoryFragment())
                            .commit()
                    }
                } else {
                    Toast.makeText(requireContext(), response.body()?.message ?: "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvRegister.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .hide(this)
                .replace(R.id.fragment_container, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}