package com.example.ecommerceproject.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email_id")
    val email: String,

    @SerializedName("password")
    val password: String
)
