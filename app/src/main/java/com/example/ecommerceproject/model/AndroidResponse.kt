package com.example.ecommerceproject.model

import android.provider.ContactsContract

class AndroidResponse(

    val message: String,
    val products: List<Product>,
    val data: List<ContactsContract.CommonDataKinds.Phone>,
    val status: Int
)