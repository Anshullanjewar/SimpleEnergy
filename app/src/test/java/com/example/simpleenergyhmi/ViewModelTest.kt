package com.example.simpleenergyhmi

import com.example.simpleenergyhmi.data.model.VehicleState
import com.example.simpleenergyhmi.data.repository.Repository
import com.example.simpleenergyhmi.viewmodel.ViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private class TestRepository(
        private val vehicleState: Flow<VehicleState>
    ) : Repository {
        override fun observeVehicleState(): Flow<VehicleState> = vehicleState
    }

    @Test
    fun `valid vehicle state updates UI state`() = runTest {
        val repository = TestRepository(
            flowOf(
                VehicleState(
                    speed = 25,
                    isMotorOn = true,
                    isKillSwitchActive = false
                )
            )
        )

        val viewModel = ViewModel(repository)

        runCurrent()

        val result = viewModel.uiState.value

        assertEquals(25, result.speed)
        assertEquals(true, result.isMotorOn)
        assertEquals(false, result.isKillSwitchActive)
        assertTrue(result.isDataAvailable)
        assertFalse(result.isStale)
        assertEquals(null, result.errorMessage)
    }

    @Test
    fun `negative speed sets invalid speed error`() = runTest {
        val repository = TestRepository(
            flowOf(
                VehicleState(
                    speed = -10,
                    isMotorOn = true,
                    isKillSwitchActive = false
                )
            )
        )

        val viewModel = ViewModel(repository)

        runCurrent()

        val result = viewModel.uiState.value

        assertEquals("Invalid Speed", result.errorMessage)
        assertFalse(result.isDataAvailable)

    }

    @Test
    fun `vehicle data becomes stale when no new reading arrives`() = runTest {
        val repository = TestRepository(
            flowOf(
                VehicleState(
                    speed = 25,
                    isMotorOn = true,
                    isKillSwitchActive = false
                )
            )
        )

        val viewModel = ViewModel(repository)

        runCurrent()

        // The initial reading should be valid.
        assertFalse(viewModel.uiState.value.isStale)

        // Advance virtual time beyond the stale timeout.
        advanceTimeBy(3_001L)
        runCurrent()

        assertTrue(viewModel.uiState.value.isStale)
        assertEquals(
            "Data Stale",
            viewModel.uiState.value.errorMessage
        )
    }

    @Test
    fun `active kill switch updates motor disabled state`() = runTest {
        val repository = TestRepository(
            flowOf(
                VehicleState(
                    speed = 0,
                    isMotorOn = false,
                    isKillSwitchActive = true
                )
            )
        )

        val viewModel = ViewModel(repository)

        runCurrent()

        val result = viewModel.uiState.value

        assertEquals(0, result.speed)
        assertEquals(false, result.isMotorOn)
        assertEquals(true, result.isKillSwitchActive)
        assertTrue(result.isDataAvailable)
        assertEquals(null, result.errorMessage)
    }
}