package com.example.ecommerceproject.model

import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("unit_price")
    val unitPrice: Int
)