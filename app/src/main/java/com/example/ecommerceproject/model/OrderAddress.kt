package com.example.ecommerceproject.model

import com.google.gson.annotations.SerializedName

data class OrderAddress (
    @SerializedName("title")
    val title: String,
    @SerializedName("address")
    val address: String
)