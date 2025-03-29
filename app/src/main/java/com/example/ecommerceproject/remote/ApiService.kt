package com.example.ecommerceproject.remote


import com.example.ecommerceproject.model.AndroidResponse
import com.example.ecommerceproject.model.CategoryResponse

import com.example.ecommerceproject.model.LoginRequest
import com.example.ecommerceproject.model.LoginResponse

import com.example.ecommerceproject.model.RegisterRequest
import com.example.ecommerceproject.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("User/auth")
    @Headers("Content-type: application/json")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("User/register")
    @Headers("Content-type: application/json")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @GET("Category")
    @Headers("Content-type: application/json")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("SubCategory/products/{sub_category_id}")
    @Headers("Content-type: application/json")
    suspend fun getProducts(@Path("sub_category_id") subCategoryId: Int): Response<AndroidResponse>

//    @GET("Product/details/{product_id}")
//    fun getProductDetails(@Path("product_id") productId: Int): Response<Details>
}