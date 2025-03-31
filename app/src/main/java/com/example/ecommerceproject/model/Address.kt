package com.example.ecommerceproject.model

import com.google.gson.annotations.SerializedName

data class Address (
    @SerializedName("address_id")
    val addressId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("address")
    val address: String
)