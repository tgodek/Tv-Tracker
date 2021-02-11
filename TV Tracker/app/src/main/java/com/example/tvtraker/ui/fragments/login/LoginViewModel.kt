package com.example.tvtraker.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvtraker.model.Korisnik
import com.example.tvtraker.model.MessageResponse
import com.example.tvtraker.repository.LoginRepository
import com.example.tvtraker.webservice.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _apiResponse: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val apiResponse: LiveData<Resource<MessageResponse>>
        get() = _apiResponse

    private val _apiResponse2: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val apiResponse2: LiveData<Resource<MessageResponse>>
        get() = _apiResponse2

    fun dohvatiKorisnika(
        kor_ime: String,
        lozinka: String
    ) = viewModelScope.launch {
        _apiResponse.value = Resource.Loading
        val response = loginRepository.dohvatiKorisnika(kor_ime, lozinka)
        _apiResponse.value = response as Resource<MessageResponse>
    }

    fun registerKorisnik(
       korisnik: Korisnik
    ) = viewModelScope.launch {
        _apiResponse2.value = Resource.Loading
        val response2 = loginRepository.registerKorisnik(korisnik)
        _apiResponse2.value = response2
    }
}