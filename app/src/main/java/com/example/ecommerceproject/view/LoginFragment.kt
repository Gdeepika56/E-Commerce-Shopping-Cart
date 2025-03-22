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
import com.example.ecommerceproject.databinding.FragmentLoginBinding
import com.example.ecommerceproject.model.LoginRequest
import com.example.ecommerceproject.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment: Fragment() {
     lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
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
                    loginUser(email,password)
                }

                binding.tvEmail.text = ""
                binding.tvPassword.text = ""
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

    private fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        val apiService: ApiService = ApiClient.retrofit.create(ApiService::class.java)

        val call = apiService.loginUser(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(!response.isSuccessful) {
                    Toast.makeText(requireContext(), "Login Failed. Try again!", Toast.LENGTH_SHORT).show()
                    return
                }

                val result = response.body()
                if(result?.status == 0) {
                    Toast.makeText(requireContext(), "Login Successful!", Toast.LENGTH_SHORT).show()


                    parentFragmentManager.beginTransaction()
                        .hide(this@LoginFragment)
                        .replace(R.id.fragment_container, CategoryFragment())
                        .commit()
                }else{
                    Toast.makeText(requireContext(),result?.message ?: "Unknown Error.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(requireContext(), "Network Error!", Toast.LENGTH_SHORT).show()
            }

        })

    }
}