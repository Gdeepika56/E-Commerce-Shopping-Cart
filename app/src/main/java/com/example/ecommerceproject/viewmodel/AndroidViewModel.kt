package com.example.ecommerceproject.viewmodel

import androidx.lifecycle.*
import com.example.ecommerceproject.model.AndroidResponse
import com.example.ecommerceproject.model.ProductDetails
import com.example.ecommerceproject.remote.ApiResult
import com.example.ecommerceproject.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AndroidViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _apiAndroidResult = MutableLiveData<ApiResult<AndroidResponse>>()
    val apiAndroidResult: LiveData<ApiResult<AndroidResponse>> = _apiAndroidResult

    private val _apiProductDetailsResult = MutableLiveData<ProductDetails>()
    val apiProductDetailsResult: LiveData<ProductDetails> = _apiProductDetailsResult

    private val _error = MutableLiveData<String> ()
    var error: LiveData<String> = _error

    fun androidAndroidList(subCategoryId: Int) {
        _apiAndroidResult.postValue(ApiResult.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getProducts(subCategoryId)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _apiAndroidResult.postValue(ApiResult.Success(it))
                    } ?: _apiAndroidResult.postValue(ApiResult.Error("Response body is null"))
                } else {
                    _apiAndroidResult.postValue(
                        ApiResult.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}")
                    )
                }
            } catch (e: Exception) {
                _apiAndroidResult.postValue(ApiResult.Error("Exception: ${e.localizedMessage}"))
            }
        }
    }

    fun getProductDetails(productId: String){
        viewModelScope.launch {
            try {
                val res = repository.getProductsDetails(productId)
                if(res.isSuccessful && res.body() != null) {
                    _apiProductDetailsResult.postValue(res.body())
                }
            }catch (e:Exception){
                _error.postValue(e.printStackTrace().toString())
            }
        }
    }
}

class PhonesViewModelFactory(private val repo: ProductRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AndroidViewModel(repo) as T
    }
}

