package com.example.simpleenergyhmi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simpleenergyhmi.ui.home.HMIScreen
import com.example.simpleenergyhmi.ui.theme.SimpleEnergyHMITheme
import com.example.simpleenergyhmi.uistate.VehicleUiState
import com.example.simpleenergyhmi.viewmodel.ViewModel
import com.example.simpleenergyhmi.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: ViewModel by viewModels{
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleEnergyHMITheme {
                val uiState by viewModel.uiState
                    .collectAsStateWithLifecycle()
                    HMIScreen(uiState = uiState)
            }
        }
    }
}

