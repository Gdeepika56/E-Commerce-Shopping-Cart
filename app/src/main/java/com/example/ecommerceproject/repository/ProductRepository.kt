package com.example.ecommerceproject.repository

import com.example.ecommerceproject.remote.ApiClient.apiService


class ProductRepository() {
    suspend fun getProducts(subCategoryId: Int) = apiService.getProducts(subCategoryId)
}