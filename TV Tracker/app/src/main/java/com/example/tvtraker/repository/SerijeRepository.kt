package com.example.tvtraker.repository

import com.example.tvtraker.webservice.RetrofitInstance

class SerijeRepository : BaseRepository() {

    suspend fun sveSerije(id:String) = safeApiCall { RetrofitInstance.api.sveSerije(id) }

    suspend fun dodaneSerije(id:String) = safeApiCall { RetrofitInstance.api.dodaneSerije(id) }

    suspend fun trendingSerije(id:String) = safeApiCall { RetrofitInstance.api.trendingSerije(id) }

    suspend fun latestSerije(id:String) = safeApiCall { RetrofitInstance.api.latestSerije(id) }

    suspend fun dohvatiIme(id:String) = safeApiCall { RetrofitInstance.api.dohvatiIme(id) }

    suspend fun dodajSeriju(serijaId: String, korisnikId: String) = safeApiCall { RetrofitInstance.api.dodajSeriju(serijaId, korisnikId) }

    suspend fun izbrisiSeriju(serijaId: String, korisnikId: String) = safeApiCall { RetrofitInstance.api.izbrisiSeriju(serijaId, korisnikId) }
}