package com.example.simpleenergyhmi.data.repository

import com.example.simpleenergyhmi.data.model.VehicleState
import kotlinx.coroutines.flow.Flow

interface Repository{
    fun observeVehicleState(): Flow<VehicleState>
}