package com.example.ecommerceproject.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("full_name")
    val full_name: String,

    @SerializedName("mobile_no")
    val mobile_no: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)