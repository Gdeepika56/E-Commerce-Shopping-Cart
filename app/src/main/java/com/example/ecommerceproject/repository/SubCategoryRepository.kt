package com.example.ecommerceproject.repository

import com.example.ecommerceproject.model.SubcategoryResponse
import com.example.ecommerceproject.remote.ApiClient.apiService
import retrofit2.Response

class SubcategoryRepository() {

    suspend fun getSubcategories(categoryId: Int): Response<SubcategoryResponse> {
        return apiService.getSubcategories(categoryId)
    }
}