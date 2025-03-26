package com.example.ecommerceproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecommerceproject.model.LoginRequest
import com.example.ecommerceproject.model.LoginResponse
import com.example.ecommerceproject.model.RegisterRequest
import com.example.ecommerceproject.model.RegisterResponse
import com.example.ecommerceproject.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val repository: AuthRepository):ViewModel() {

    private val _loginResponse = MutableLiveData<Response<LoginResponse>>()
    val loginResponse:  LiveData<Response<LoginResponse>> get() = _loginResponse

    private val _registerResponse = MutableLiveData<Response<RegisterResponse>>()
    val registerResponse: LiveData<Response<RegisterResponse>> get() = _registerResponse

    fun loginUser(email:String, password:String) {
        viewModelScope.launch{
            val response = repository.loginUser(LoginRequest(email,password))
            _loginResponse.postValue(response)
        }

    }

    fun registerUser(fullName: String, mobile_no: String, email:String, password: String){
        viewModelScope.launch {
            val response = repository.registerUser(RegisterRequest(fullName, mobile_no, email, password ))
            _registerResponse.postValue(response)
        }
    }

}

class AuthViewModelFactory(private val repository: AuthRepository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthViewModel(repository) as T

    }
}
