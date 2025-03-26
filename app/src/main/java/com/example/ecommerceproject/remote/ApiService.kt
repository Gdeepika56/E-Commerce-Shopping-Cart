package com.example.ecommerceproject.remote


import com.example.ecommerceproject.model.AndroidResponse
import com.example.ecommerceproject.model.CategoryResponse
import com.example.ecommerceproject.model.LoginRequest
import com.example.ecommerceproject.model.LoginResponse
import com.example.ecommerceproject.model.RegisterRequest
import com.example.ecommerceproject.model.RegisterResponse
import com.example.ecommerceproject.model.SubcategoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("User/auth")
    @Headers("Content-type: application/json")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>

    @POST("User/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @GET("Category")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("SubCategory")
    suspend fun getSubcategories(@Query("category_id") categoryId: Int): Response<SubcategoryResponse>


    @GET("SubCategory/products/{sub_category_id}")
    suspend fun getProducts(@Path("sub_category_id") subCategoryId: Int): Response<AndroidResponse>
}