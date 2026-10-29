package com.example.simpleenergyhmi.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleenergyhmi.data.model.VehicleState
import com.example.simpleenergyhmi.data.repository.Repository
import com.example.simpleenergyhmi.uistate.VehicleUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState = MutableStateFlow(VehicleUiState())
    val uiState: StateFlow<VehicleUiState> = _uiState

    private var observeVehicleStateJob: Job? = null
    private var staleCheckJob: Job? = null

    companion object {
        private const val STALE_CHECK_INTERVAL = 3_000L
    }

    init {
        observeVehicleData()
    }

    private fun observeVehicleData() {
        observeVehicleStateJob = viewModelScope.launch {
            repository.observeVehicleState().catch { exception ->
                _uiState.value = VehicleUiState(
                    errorMessage = exception.message ?: "Unable to read Data"
                )
            }
                .collect { vehicleState ->
                    handleVehicleState(vehicleState)
                }
        }
    }

    private fun handleVehicleState(vehicleState: VehicleState) {
        if(vehicleState.speed < 0 ){
            _uiState.value = VehicleUiState(
                errorMessage = "Invalid Speed"
            )
            return
        }

        _uiState.value = VehicleUiState(
            speed = vehicleState.speed,
            isMotorOn = vehicleState.isMotorOn,
            isKillSwitchActive = vehicleState.isKillSwitchActive,
            isDataAvailable = true,
            isStale = false,
            errorMessage = null
        )

        startStaleCheck()
    }

    private fun startStaleCheck() {
        staleCheckJob?.cancel()

        staleCheckJob = viewModelScope.launch {
            delay(STALE_CHECK_INTERVAL)

            _uiState.value = _uiState.value.copy(
                isStale = true,
                errorMessage = "Data Stale"
            )
        }
    }

    override fun onCleared() {
        observeVehicleStateJob?.cancel()
        staleCheckJob?.cancel()
        super.onCleared()
    }
}