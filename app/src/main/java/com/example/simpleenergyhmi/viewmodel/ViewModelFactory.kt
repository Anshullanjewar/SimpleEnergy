package com.example.simpleenergyhmi.viewmodel

import androidx.lifecycle.ViewModel as LifecycleViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simpleenergyhmi.data.repository.Repository
import com.example.simpleenergyhmi.data.repository.RepositoryImpl
import com.example.simpleenergyhmi.data.source.VehicleDataProvider

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : LifecycleViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModel::class.java)) {
            val vehicleDataProvider = VehicleDataProvider()
            val repository = RepositoryImpl(vehicleDataProvider)
            @Suppress("UNCHECKED_CAST")
            return ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}