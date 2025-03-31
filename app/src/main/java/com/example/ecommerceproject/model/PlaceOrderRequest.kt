package com.example.ecommerceproject.model

import com.google.gson.annotations.SerializedName

data class PlaceOrderRequest(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("delivery_address")
    val deliverAddress: OrderAddress,
    @SerializedName("items")
    val items: List<Items>,
    @SerializedName("bill_amount")
    val billAmount: Long,
    @SerializedName("payment_method")
    val paymentMethod: String
)
