package com.example.ecommerceproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceproject.model.CategoryResponse
import com.example.ecommerceproject.remote.ApiClient
import com.example.ecommerceproject.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CategoryRepository {

    private val apiService: ApiService = ApiClient.apiService

    private val _categories = MutableLiveData<CategoryResponse>()
    val categories: LiveData<CategoryResponse> get() = _categories

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    suspend fun fetchCategories() {
       withContext(Dispatchers.IO)  {
           try{
               val response: Response<CategoryResponse> = apiService.getCategories()
               if(response.isSuccessful) {
                   response.body()?.let{
                       _categories.postValue(it)
                   }?: run{
                       _error.postValue("No Categories found")
                   }
               }else{
                   _error.postValue("Failed to load categories")
               }
           }catch (e: Exception) {
               _error.postValue("Network Error! ${e.localizedMessage}")
           }
       }
    }
}