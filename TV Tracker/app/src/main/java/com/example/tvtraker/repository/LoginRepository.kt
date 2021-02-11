package com.example.tvtraker.repository

import com.example.tvtraker.model.Korisnik
import com.example.tvtraker.webservice.RetrofitInstance

class LoginRepository : BaseRepository() {

    suspend fun dohvatiKorisnika(kor_ime: String, lozinka: String) = safeApiCall { RetrofitInstance.api.dohvatiKorisnika(kor_ime, lozinka) }

    suspend fun registerKorisnik(
       korisnik: Korisnik
    ) = safeApiCall { RetrofitInstance.api.registerKorisnik(korisnik) }
}