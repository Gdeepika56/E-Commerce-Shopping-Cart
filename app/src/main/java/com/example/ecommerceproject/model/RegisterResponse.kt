package com.example.ecommerceproject.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String
)