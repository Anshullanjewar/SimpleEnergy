package com.example.simpleenergyhmi.data.repository

import com.example.simpleenergyhmi.data.model.VehicleState
import com.example.simpleenergyhmi.data.source.VehicleDataProvider
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val vehicleDataProvider: VehicleDataProvider
): Repository {
    override fun observeVehicleState(): Flow<VehicleState> {
        return vehicleDataProvider.observeVehicleState()
    }
}