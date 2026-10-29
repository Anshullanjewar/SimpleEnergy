package com.example.simpleenergyhmi.data.model

data class VehicleState (
    val speed : Int,
    val isMotorOn : Boolean,
    val isKillSwitchActive : Boolean,
)