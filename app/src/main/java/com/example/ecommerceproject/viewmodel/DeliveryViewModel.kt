package com.example.ecommerceproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecommerceproject.model.Address
import com.example.ecommerceproject.model.DeliveryAddressRequest
import com.example.ecommerceproject.model.DeliveryAddressResponse
import com.example.ecommerceproject.model.GetDeliveryAddressResponse
import com.example.ecommerceproject.remote.ApiResult
import com.example.ecommerceproject.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DeliveryViewModel(private val repository: ProductRepository): ViewModel() {

    val selectedPaymentMode = MutableLiveData<String>()

    val selectedShippingAddress = MutableLiveData<Address>()

    private val _apiStateAddAddress = MutableLiveData<ApiResult<DeliveryAddressResponse>>()
    val apiStateAddAddress: LiveData<ApiResult<DeliveryAddressResponse>> = _apiStateAddAddress

    private val _apiStateGetAddress = MutableLiveData<ApiResult<GetDeliveryAddressResponse>>()
    val apiStateGetAddress: LiveData<ApiResult<GetDeliveryAddressResponse>> = _apiStateGetAddress


    fun addDeliveryAddress(address: DeliveryAddressRequest){
        viewModelScope.launch(Dispatchers.IO) {

            try {

                val response: Response<DeliveryAddressResponse> = repository.addDeliveryAddress(address)

                if (!response.isSuccessful){
                    _apiStateAddAddress.postValue(ApiResult.Error("Failed to Add Address"))
                    return@launch
                }

                val result = response.body()
                if (result == null){
                    _apiStateAddAddress.postValue(ApiResult.Error("Empty Response from the server. Please retry."))
                    return@launch
                }

                if (result.status == 0){
                    _apiStateAddAddress.postValue(ApiResult.Success(result))
                }
                else{
                    _apiStateAddAddress.postValue(ApiResult.Error(result.message))
                }
            }
            catch (e: Exception){
                _apiStateAddAddress.postValue(ApiResult.Error("Error is e: $e"))
            }
        }
    }


    fun getAddress(userId: Int){
        viewModelScope.launch(Dispatchers.IO) {

            try {

                val response: Response<GetDeliveryAddressResponse> = repository.getDeliveryAddresses(userId)

                if (!response.isSuccessful){
                    _apiStateGetAddress.postValue(ApiResult.Error("Failed to Get delivery Addresses"))
                    return@launch
                }

                val result = response.body()
                if (result == null){
                    _apiStateGetAddress.postValue(ApiResult.Error("Empty Response from the server. Please retry."))
                    return@launch
                }

                if (result.status == 0){
                    _apiStateGetAddress.postValue(ApiResult.Success(result))
                }
                else{
                    _apiStateGetAddress.postValue(ApiResult.Error(result.message))
                }
            }
            catch (e: Exception){
                _apiStateGetAddress.postValue(ApiResult.Error("Error is e: $e"))
            }
        }
    }

}

class DeliveryVieModelFactory(private val repository: ProductRepository): ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DeliveryViewModel(repository) as T
    }


}