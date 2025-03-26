package com.example.ecommerceproject.viewmodel

import androidx.lifecycle.*
import com.example.ecommerceproject.model.AndroidResponse
import com.example.ecommerceproject.remote.ApiResult
import com.example.ecommerceproject.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AndroidViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _apiAndroidResult = MutableLiveData<ApiResult<AndroidResponse>>()
    val apiAndroidResult: LiveData<ApiResult<AndroidResponse>> = _apiAndroidResult

    fun androidAndroidList(subCategoryId: Int) {
        _apiAndroidResult.postValue(ApiResult.Loading) // Set Loading state

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
}

class PhonesViewModelFactory(private val repo: ProductRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AndroidViewModel(repo) as T
    }
}

