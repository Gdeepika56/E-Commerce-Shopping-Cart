package com.example.ecommerceproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ecommerceproject.model.remote.ApiClient
import com.example.ecommerceproject.model.remote.ApiService
import com.example.ecommerceproject.R
import com.example.ecommerceproject.databinding.FragmentRegisterBinding
import com.example.ecommerceproject.model.RegisterRequest
import com.example.ecommerceproject.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment: Fragment() {
    lateinit var binding: FragmentRegisterBinding

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
                    registerUser(fullName,mobile,email,password)
                }
            }
        }

        binding.tvAccount.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .hide(this)
                .replace(R.id.fragment_container, LoginFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun registerUser(fullName: String, mobile: String, email: String, password: String) {
        val registerRequest = RegisterRequest(fullName, mobile,email,password)
        val apiService: ApiService = ApiClient.retrofit.create(ApiService::class.java)

        val call = apiService.registerUser(registerRequest)
        call.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(!response.isSuccessful){
                    Toast.makeText(requireContext(),"Registration Failed. Try Again", Toast.LENGTH_SHORT).show()
                    return
                }

                val result = response.body()
                if (result?.status == 0) {
                    Toast.makeText(requireContext(), "Registration Successful!", Toast.LENGTH_SHORT).show()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LoginFragment())
                        .commit()
                } else {
                    Toast.makeText(requireContext(), result?.message ?: "Unknown Error.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(requireContext(), "Network Error!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}