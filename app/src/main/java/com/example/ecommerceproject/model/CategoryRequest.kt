package com.example.ecommerceproject.model

import com.google.gson.annotations.SerializedName

data class CategoryRequest(
    @SerializedName("category_id")
    val category_id: String,
    @SerializedName("category_name")
    val category_name: String,
    @SerializedName("category_image_url")
    val category_image_url: String,

)