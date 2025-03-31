package com.example.ecommerceproject.model

import com.google.gson.annotations.SerializedName

data class DeliveryAddressResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String
)