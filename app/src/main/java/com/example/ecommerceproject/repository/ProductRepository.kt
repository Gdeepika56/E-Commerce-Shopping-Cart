package com.example.ecommerceproject.repository

import com.example.ecommerceproject.model.DeliveryAddressRequest
import com.example.ecommerceproject.model.PlaceOrderRequest
import com.example.ecommerceproject.remote.ApiService


class ProductRepository(private val apiService: ApiService) {
    suspend fun getProducts(subCategoryId: Int) = apiService.getProducts(subCategoryId)

    suspend fun getProductsDetails(productId: String) = apiService.getProductDetails(productId)

    suspend fun addDeliveryAddress(address: DeliveryAddressRequest) = apiService.addDeliveryAddress(address)

    suspend fun getDeliveryAddresses( userId: Int) = apiService.getDeliveryAddresses(userId)

    suspend fun postOrder(placeOrder: PlaceOrderRequest) = apiService.postOrder(placeOrder)


}