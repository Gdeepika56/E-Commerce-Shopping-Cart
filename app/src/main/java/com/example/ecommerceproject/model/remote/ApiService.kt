package com.example.ecommerceproject.model.remote


import com.example.ecommerceproject.model.CategoryResponse
import com.example.ecommerceproject.model.LoginRequest
import com.example.ecommerceproject.model.LoginResponse
import com.example.ecommerceproject.model.RegisterRequest
import com.example.ecommerceproject.model.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("User/auth")
    @Headers("Content-type: application/json")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("User/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @GET("Category")
    suspend fun getCategories(): Response<CategoryResponse>
}