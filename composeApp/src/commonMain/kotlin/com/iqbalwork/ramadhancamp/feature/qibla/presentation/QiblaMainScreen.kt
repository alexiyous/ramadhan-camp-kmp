package com.iqbalwork.ramadhancamp.feature.qibla.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.components.CompassDial
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.components.QiblaNoLocationPlaceholder
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.model.QiblaEvent
import com.iqbalwork.ramadhancamp.feature.qibla.presentation.model.QiblaState
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import kotlin.math.roundToInt
import org.koin.core.parameter.parametersOf

@Composable
fun QiblaMainScreen() {
    val viewModel: QiblaViewModel = rememberViewModel(parameters = { parametersOf(QiblaScreenParameters()) })
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatch = viewModel.rememberDispatch()

    QiblaContent(state = state, action = dispatch)
}

@Composable
fun QiblaContent(
    state: QiblaState,
    action: (QiblaEvent) -> Unit
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    if (!state.hasLocationPermission) {
        QiblaNoLocationPlaceholder(
            onRequestPermission = { action(QiblaEvent.RequestLocation) },
            modifier = Modifier
                .fillMaxSize()
                .background(colors.bgPrimary)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.bgPrimary)
                .padding(horizontal = 24.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Arah Kiblat",
                    style = typography.headlineLarge,
                    color = colors.textPrimary
                )
                IconButton(onClick = { /* TODO Info Action */ }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = colors.textMuted
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Surface(
                color = colors.bgSecondary,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.wrapContentWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = colors.accentPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = if (state.isLoading) "Mencari Lokasi..." else state.cityName,
                        style = typography.bodyLarge,
                        color = colors.textPrimary
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            CompassDial(
                heading = state.currentHeading,
                bearingToKaaba = state.bearingToKaaba,
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                text = "Kiblat",
                style = typography.headlineSmall,
                color = colors.textPrimary,
                fontWeight = FontWeight.Bold
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = colors.accentGold,
                    strokeWidth = 3.dp
                )
            } else {
                Text(
                    text = "${state.bearingToKaaba?.roundToInt() ?: 0}°",
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
                Text(
                    text = getIndonesianCardinal(state.bearingToKaaba ?: 0f),
                    style = typography.bodyLarge,
                    color = colors.textMuted
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

private fun getIndonesianCardinal(degree: Float): String {
    val normalized = (degree % 360 + 360) % 360
    return when {
        normalized >= 337.5 || normalized < 22.5 -> "UTARA"
        normalized >= 22.5 && normalized < 67.5 -> "TIMUR LAUT"
        normalized >= 67.5 && normalized < 112.5 -> "TIMUR"
        normalized >= 112.5 && normalized < 157.5 -> "TENGGARA"
        normalized >= 157.5 && normalized < 202.5 -> "SELATAN"
        normalized >= 202.5 && normalized < 247.5 -> "BARAT DAYA"
        normalized >= 247.5 && normalized < 292.5 -> "BARAT"
        normalized >= 292.5 && normalized < 337.5 -> "BARAT LAUT"
        else -> "UTARA"
    }
}
