package com.example.ecommerceproject.remote


import com.example.ecommerceproject.model.AndroidResponse
import com.example.ecommerceproject.model.CategoryResponse
import com.example.ecommerceproject.model.DeliveryAddressRequest
import com.example.ecommerceproject.model.DeliveryAddressResponse
import com.example.ecommerceproject.model.GetDeliveryAddressResponse

import com.example.ecommerceproject.model.LoginRequest
import com.example.ecommerceproject.model.LoginResponse
import com.example.ecommerceproject.model.PlaceOrderRequest
import com.example.ecommerceproject.model.PlaceOrderResponse
import com.example.ecommerceproject.model.ProductDetails

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

    @GET("Product/details/{product_id}")
    @Headers("Content-type: application/json")
    suspend fun getProductDetails(@Path("product_id") productId: String): Response<ProductDetails>

    @POST("User/address")
    @Headers("Content-type: application/json")
    suspend fun addDeliveryAddress(@Body request: DeliveryAddressRequest): Response<DeliveryAddressResponse>

    @GET("User/addresses/{user_id}")
    @Headers("Content-type: application/json")
    suspend fun getDeliveryAddresses(@Path("user_id") userId: Int): Response<GetDeliveryAddressResponse>

    @POST("Order")
    @Headers("Content-type: application/json")
    suspend fun postOrder(@Body placeOrder: PlaceOrderRequest): Response<PlaceOrderResponse>
}