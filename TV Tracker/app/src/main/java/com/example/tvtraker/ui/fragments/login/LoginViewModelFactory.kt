package com.example.tvtraker.ui.fragments.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvtraker.repository.LoginRepository

class LoginViewModelFactory(private val loginRepository: LoginRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(loginRepository) as T
    }
}