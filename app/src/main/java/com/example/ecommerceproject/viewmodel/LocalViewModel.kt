package com.example.ecommerceproject.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import com.example.ecommerceproject.data.CartItem
import com.example.ecommerceproject.repository.LocalRepository

class LocalViewModel(private val repo: LocalRepository): ViewModel() {

    val error: LiveData<String> = repo.error

    val cartItems: LiveData<List<CartItem>> = repo.cartItems

    val totalPrice: LiveData<Double> = cartItems.map { item ->
        item?.sumOf { it.quantity * it.unitPrice }?.toDouble() ?: 0.0
    }

    fun addCartItem(cartItem: CartItem){
        repo.addCartItem(cartItem)
    }

    fun getAllCartItems(){
        repo.getAllCartItems()
    }

    fun increaseQuantity(productID: Int){
        repo.increaseQuantity(productID)
    }

    fun decreaseQuantity(productID: Int){
        repo.decreaseQuantity(productID)
        repo.deleteIfZero(productID)
    }

    fun getQuantityOfProduct(productID: Int): LiveData<Int> {
        return repo.getQuantityOfProduct(productID)
    }
}

class LocalViewModelFactory(private val repo: LocalRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocalViewModel(repo) as T
    }
}
