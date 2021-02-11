package com.example.tvtraker.ui.fragments.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvtraker.repository.SerijeRepository

class SerijeViewModelFactory(private val repository: SerijeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SerijeViewModel(repository) as T
    }
}