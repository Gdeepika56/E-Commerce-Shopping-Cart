package com.example.ecommerceproject.viewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecommerceproject.model.PlaceOrderRequest
import com.example.ecommerceproject.model.PlaceOrderResponse
import com.example.ecommerceproject.remote.ApiResult
import com.example.ecommerceproject.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class PlaceOrderViewModel(private val repository: ProductRepository):ViewModel() {

    private val _apiStatePlaceOrder = MutableLiveData<ApiResult<PlaceOrderResponse>>()
    val apiStatePlaceOrder: LiveData<ApiResult<PlaceOrderResponse>> = _apiStatePlaceOrder

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun placeFinalOrder(placeOrder: PlaceOrderRequest) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { repository.postOrder(placeOrder) }

                when {
                    !response.isSuccessful -> _apiStatePlaceOrder.postValue(ApiResult.Error("Order placement failed: ${response.message()}"))

                    response.body() == null -> _apiStatePlaceOrder.postValue(ApiResult.Error("Empty response from server. Please retry."))

                    response.body()?.status == 0 -> _apiStatePlaceOrder.postValue(ApiResult.Success(response.body()!!))

                    else -> _apiStatePlaceOrder.postValue(ApiResult.Error(response.body()?.message ?: "Unknown error occurred"))
                }
            } catch (e: IOException) {
                _apiStatePlaceOrder.postValue(ApiResult.Error("Network error: ${e.localizedMessage}"))
            } catch (e: HttpException) {
                _apiStatePlaceOrder.postValue(ApiResult.Error("HTTP error: ${e.localizedMessage}"))
            } catch (e: Exception) {
                _apiStatePlaceOrder.postValue(ApiResult.Error("Unexpected error: ${e.localizedMessage}"))
            }
        }
    }

}

class OrderViewModelFactory(private val repository: ProductRepository): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaceOrderViewModel(repository) as T
    }
}