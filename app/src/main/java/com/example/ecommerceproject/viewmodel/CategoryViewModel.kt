package com.example.ecommerceproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceproject.model.CategoryResponse
import com.example.ecommerceproject.model.remote.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel: ViewModel() {
    private val repository = CategoryRepository()

    val categories: LiveData<CategoryResponse> get() = repository.categories
    val error: LiveData<String> get() = repository.error

    fun fetchCategories() {
        viewModelScope.launch {
            repository.fetchCategories()
        }
    }
}