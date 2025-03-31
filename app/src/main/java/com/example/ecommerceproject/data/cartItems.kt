package com.example.ecommerceproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartitems")
data class CartItem(

    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: Int,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "unit_price")
    val unitPrice: Int,

    @ColumnInfo(name = "product_name")
    val productName: String,

    @ColumnInfo(name = "description")
    val description:String,

    @ColumnInfo(name = "product_image_url")
    val imageUrl: String


)