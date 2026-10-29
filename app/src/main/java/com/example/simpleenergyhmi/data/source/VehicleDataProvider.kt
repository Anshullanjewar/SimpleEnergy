package com.example.simpleenergyhmi.data.source

import com.example.simpleenergyhmi.data.model.VehicleState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class VehicleDataProvider {

    fun observeVehicleState(): Flow<VehicleState> = flow{
        val simulation = listOf(
            VehicleState(speed = 0, isMotorOn = false, isKillSwitchActive = false), //parked
            VehicleState(speed = 0, isMotorOn = true, isKillSwitchActive = false), //ready
            VehicleState(speed = 25, isMotorOn = true, isKillSwitchActive = false), //riding
            VehicleState(speed = 50, isMotorOn = true, isKillSwitchActive = false), //acc
            VehicleState(speed = 0, isMotorOn = false, isKillSwitchActive = true), //motor disabled

        )

        while (true) {
            for (state in simulation) {
                emit(state)
                delay(1_000L)
            }
        }
    }
}