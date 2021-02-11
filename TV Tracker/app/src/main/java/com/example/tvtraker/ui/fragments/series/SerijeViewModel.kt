package com.example.tvtraker.ui.fragments.series

import androidx.lifecycle.*
import com.example.tvtraker.model.MessageResponse
import com.example.tvtraker.model.TvSerija
import com.example.tvtraker.model.UsernameResponse
import com.example.tvtraker.repository.SerijeRepository
import com.example.tvtraker.webservice.Resource
import kotlinx.coroutines.launch

class SerijeViewModel(private val repository: SerijeRepository) : ViewModel() {

    //preporuke serija korisniku na temelju dodanih serija i njihovih zanrova
    private val _apiResponse: MutableLiveData<Resource<List<TvSerija>>> = MutableLiveData()
    val apiResponse: LiveData<Resource<List<TvSerija>>>
        get() = _apiResponse

    //dodaj seriju korisniku
    private val _apiResponse2: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val apiResponse2: LiveData<Resource<MessageResponse>>
        get() = _apiResponse2

    //serije koje se nalaze u korisnikovoj kolekciji
    private val _apiResponse3: MutableLiveData<Resource<List<TvSerija>>> = MutableLiveData()
    val apiResponse3: LiveData<Resource<List<TvSerija>>>
        get() = _apiResponse3

    //izbrisi seriju korisniku
    private val _apiResponse4: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()
    val apiResponse4: LiveData<Resource<MessageResponse>>
        get() = _apiResponse4

    //serije koje treuntno u trendingu - prema trenutno najvise dodanim serijama medu korisnicima
    private val _apiResponse5: MutableLiveData<Resource<List<TvSerija>>> = MutableLiveData()
    val apiResponse5: LiveData<Resource<List<TvSerija>>>
        get() = _apiResponse5

    //najnovije serije dodane u bazu serija
    private val _apiResponse6: MutableLiveData<Resource<List<TvSerija>>> = MutableLiveData()
    val apiResponse6: LiveData<Resource<List<TvSerija>>>
        get() = _apiResponse6

    //dohvati korisnicko ime
    private val _apiResponse7: MutableLiveData<Resource<List<UsernameResponse>>> = MutableLiveData()
    val apiResponse7: LiveData<Resource<List<UsernameResponse>>>
        get() = _apiResponse7

    /************************************** Upiti prema API-u **************************************/

    fun sveSerije(id:String) = viewModelScope.launch {
        _apiResponse.value = Resource.Loading
        val response = repository.sveSerije(id)
        _apiResponse.value = response
    }

    fun dodajSeriju(serijaId: String, korisnikId: String) = viewModelScope.launch {
        _apiResponse2.value = Resource.Loading
        val response = repository.dodajSeriju(serijaId, korisnikId)
        _apiResponse2.value = response
    }

    fun dodaneSerije(id:String) = viewModelScope.launch {
        _apiResponse3.value = Resource.Loading
        val response = repository.dodaneSerije(id)
        _apiResponse3.value = response
    }

    fun izbrisiSeriju(serijaId: String, korisnikId: String) = viewModelScope.launch {
        _apiResponse4.value = Resource.Loading
        val response = repository.izbrisiSeriju(serijaId, korisnikId)
        _apiResponse4.value = response
    }

    fun trendingSerije(id: String) = viewModelScope.launch {
        _apiResponse5.value = Resource.Loading
        val response = repository.trendingSerije(id)
        _apiResponse5.value = response
    }

    fun latestSerije(id: String) = viewModelScope.launch {
        _apiResponse6.value = Resource.Loading
        val response = repository.latestSerije(id)
        _apiResponse6.value = response
    }

    fun dohvatiIme(id: String) = viewModelScope.launch {
        val response = repository.dohvatiIme(id)
        _apiResponse7.value = response
    }
}