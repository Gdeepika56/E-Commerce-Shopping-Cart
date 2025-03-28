package com.example.ecommerceproject.model


data class Details(
    val productId: String,
    val productName: String,
    val description: String,
    val categoryId: String,
    val subCategoryId: String,
    val price: String,
    val averageRating: String,
    val productImageUrl: String,
    val isActive: String,
    val images: List<ProductImage>,
    val specifications: List<Specification>,
    val reviews: List<Review>
)

data class ProductImage(val image: String, val displayOrder: Int)
data class Specification(val specificationId: String, val title: String, val specification: String, val displayOrder: Int)
data class Review(val userId: String, val fullName: String, val reviewId: String, val reviewTitle: String, val review: String, val rating: String, val reviewDate: String)
