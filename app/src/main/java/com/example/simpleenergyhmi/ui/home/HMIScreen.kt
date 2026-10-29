package com.example.simpleenergyhmi.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpleenergyhmi.ui.theme.DashboardBlack
import com.example.simpleenergyhmi.ui.theme.DashboardGreen
import com.example.simpleenergyhmi.ui.theme.DashboardRed
import com.example.simpleenergyhmi.ui.theme.MutedText
import com.example.simpleenergyhmi.ui.theme.PanelBlack
import com.example.simpleenergyhmi.uistate.VehicleUiState

@Composable
fun HMIScreen(
    uiState: VehicleUiState,
    modifier: Modifier = Modifier
) {
    val hasValidData =
        uiState.isDataAvailable &&
                !uiState.isStale &&
                uiState.errorMessage == null

    val speed = if (hasValidData) {
        uiState.speed.toString()
    } else {
        "--"
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = DashboardBlack
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 10.dp)
        ) {
            //Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SIMPLE ENERGY HMI",
                    color = MutedText,
                    fontSize = 11.sp,
                    letterSpacing = 1.5.sp
                )

                Text(
                    text = when {
                        uiState.isStale -> "● STALE DATA"
                        uiState.errorMessage != null -> "● DATA ERROR"
                        !uiState.isDataAvailable -> "● WAITING FOR DATA"
                        else -> "● CONNECTED"
                    },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(4.dp))

            //DashBoard
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "RIDE",
                        color = DashboardGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 5.sp
                    )

                    Spacer(Modifier.height(2.dp))

                    Text(
                        text = speed,
                        color = Color.White,
                        fontSize = 88.sp,
                        fontWeight = FontWeight.Light,
                        lineHeight = 92.sp
                    )

                    Text(
                        text = "KM / H",
                        color = MutedText,
                        fontSize = 10.sp,
                        letterSpacing = 4.sp
                    )

                    Spacer(Modifier.height(2.dp))

                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .height(34.dp)
                    ) {
                        drawArc(
                            color = Color(0xFF34383B),
                            startAngle = 200f,
                            sweepAngle = 140f,
                            useCenter = false,
                            style = Stroke(
                                width = 13.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )

                        drawArc(
                            color = DashboardRed,
                            startAngle = 200f,
                            sweepAngle = if (hasValidData) {
                                (uiState.speed.toFloat() / 100f)
                                    .coerceIn(0f, 1f) * 140f
                            } else {
                                0f
                            },
                            useCenter = false,
                            style = Stroke(
                                width = 13.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )
                    }

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = "VEHICLE SPEED",
                        color = MutedText,
                        fontSize = 10.sp,
                        letterSpacing = 2.sp
                    )
                }

                //Status Buttons

                Column(
                    modifier = Modifier
                        .width(175.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    StatusPanel(
                        title = "MOTOR",
                        value = when (uiState.isMotorOn) {
                            true -> "ON"
                            false -> "OFF"
                        },
                        active = uiState.isMotorOn == true,
                        accent = DashboardGreen
                    )

                    Spacer(Modifier.height(10.dp))

                    StatusPanel(
                        title = "KILL SWITCH",
                        value = when (uiState.isKillSwitchActive) {
                            true -> "ACTIVE"
                            false -> "INACTIVE"
                        },
                        active = uiState.isKillSwitchActive == true,
                        accent = DashboardRed
                    )

                    Spacer(Modifier.height(10.dp))

                    StatusPanel(
                        title = "DATA LINK",
                        value = when {
                            uiState.isStale -> "STALE"
                            uiState.errorMessage != null -> "ERROR"
                            uiState.isDataAvailable -> "CONNECTED"
                            else -> "WAITING"
                        },
                        active = hasValidData,
                        accent = DashboardGreen
                    )
                }
            }

            //Footer Info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PanelBlack)
                    .clip(RoundedCornerShape(18.dp))
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "RIDE MODE",
                        color = MutedText,
                        fontSize = 9.sp,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = when {
                            !hasValidData -> "UNAVAILABLE"
                            uiState.isKillSwitchActive == true -> "MOTOR DISABLED"
                            uiState.isMotorOn == true &&
                                    (uiState.speed) >= 50 -> "ACCELERATING"

                            uiState.isMotorOn == true &&
                                    (uiState.speed) > 0 -> "RIDING"

                            uiState.isMotorOn == true -> "READY"
                            else -> "PARKED"
                        },
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


@Composable
private fun StatusPanel(
    title: String,
    value: String,
    active: Boolean,
    accent: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(PanelBlack)
            .padding(horizontal = 13.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (active) accent else MutedText
                    )
            )

            Spacer(Modifier.width(7.dp))

            Text(
                text = title,
                color = MutedText,
                fontSize = 9.sp,
                letterSpacing = 1.sp
            )

        }
        Spacer(Modifier.height(5.dp))

        Text(
            text = value,
            color = if (active) accent else Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}