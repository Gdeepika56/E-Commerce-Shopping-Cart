package com.example.ecommerceproject.repository

import com.example.ecommerceproject.model.LoginRequest
import com.example.ecommerceproject.model.LoginResponse
import com.example.ecommerceproject.model.RegisterRequest
import com.example.ecommerceproject.model.RegisterResponse
import com.example.ecommerceproject.remote.ApiClient
import com.example.ecommerceproject.remote.ApiService
import retrofit2.Response

class AuthRepository {

    private val apiService: ApiService = ApiClient.apiService


    suspend fun loginUser(request: LoginRequest): Response<LoginResponse> {
        return apiService.loginUser(request)
    }

    suspend fun registerUser(request: RegisterRequest): Response<RegisterResponse> {
        return apiService.registerUser(request)
    }
}