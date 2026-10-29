package com.example.simpleenergyhmi.uistate

data class VehicleUiState(
    val speed: Int = 0,
    val isMotorOn: Boolean = false,
    val isKillSwitchActive: Boolean = false,
    val isDataAvailable: Boolean = false,
    val isStale: Boolean = false,
    val errorMessage: String? = null
)