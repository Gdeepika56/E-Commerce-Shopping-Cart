package com.example.ecommerceproject.model.remote


import com.example.ecommerceproject.model.CategoryResponse
import com.example.ecommerceproject.model.LoginRequest
import com.example.ecommerceproject.model.LoginResponse
import com.example.ecommerceproject.model.RegisterRequest
import com.example.ecommerceproject.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("User/auth")
    @Headers("Content-type: application/json")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("User/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("Category")
    fun getCategories(): Call<CategoryResponse>
}