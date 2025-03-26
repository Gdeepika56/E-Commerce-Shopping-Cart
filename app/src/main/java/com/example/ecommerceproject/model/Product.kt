package com.example.ecommerceproject.model

data class Product(
    val productid: String,
    val productname: String,
    val description: String,
    val categoryid: String,
    val categoryname: String,
    val subcategoryid: String,
    val subcategoryname: String,
    val price: String,
    val averagerating: Float,
    val productimage_url: String
)



