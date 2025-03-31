package com.example.ecommerceproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceproject.data.AppDatabase
import com.example.ecommerceproject.data.CartItem

class LocalRepository(private val appDatabase: AppDatabase) {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    val cartItems: LiveData<List<CartItem>> = getAllCartItems()

    fun addCartItem(cartItem: CartItem) {
        try {
            appDatabase.cartDao.addCartItem(cartItem)
        }
        catch (e: Exception){
            _error.postValue("Error is e: $e")
        }
    }

    fun getAllCartItems(): LiveData<List<CartItem>> {
        try {
            return appDatabase.cartDao.getAllCartItems()
        }
        catch (e: Exception){
            _error.postValue("Error is e: $e")
            return MutableLiveData(emptyList<CartItem>())
        }
    }

    fun increaseQuantity(productId: Int) {
        try {
            appDatabase.cartDao.increaseQuantity(productId)
        }
        catch (e: Exception){
            _error.postValue("Error is e: $e")
        }
    }

    fun decreaseQuantity(productId: Int) {
        try {
            appDatabase.cartDao.decreaseQuantity(productId)
        }
        catch (e: Exception){
            _error.postValue("Error is e: $e")
        }
    }

    fun deleteIfZero(productId: Int) {
        try {
            appDatabase.cartDao.deleteIfZero(productId)
        }
        catch (e: Exception){
            _error.postValue("Error is e: $e")
        }
    }

    fun getQuantityOfProduct(productId: Int): LiveData<Int> {
        try {
            return appDatabase.cartDao.getQuantityOfProduct(productId)
        }
        catch (e: Exception){
            _error.postValue("Error is e: $e")
            return MutableLiveData(-1)
        }
    }
}