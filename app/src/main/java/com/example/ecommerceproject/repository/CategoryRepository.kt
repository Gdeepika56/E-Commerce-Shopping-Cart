package com.example.ecommerceproject.model.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceproject.model.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRepository {

    private val apiService: ApiService = ApiClient.retrofit.create(ApiService::class.java)

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