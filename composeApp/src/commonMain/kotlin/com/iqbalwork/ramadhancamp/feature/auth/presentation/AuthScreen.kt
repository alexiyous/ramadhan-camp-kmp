package com.iqbalwork.ramadhancamp.feature.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationControllerHolder
import com.iqbalwork.ramadhancamp.shared.common.navigation.RootDestination
import com.iqbalwork.ramadhancamp.shared.common.presentation.DemoButton
import com.iqbalwork.ramadhancamp.shared.common.presentation.DemoSection
import org.koin.compose.koinInject

@Composable
fun AuthScreen() {
    val navHolder: AppNavigationControllerHolder = koinInject()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("Auth", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "Arrived via navigateTo, navigateTo(replace), or startNewFlow from HomeMain.",
            style = MaterialTheme.typography.bodySmall,
        )
        DemoSection("Root Navigation") {
            DemoButton("Navigate to Main (push)") {
                navHolder.get().navigateTo(RootDestination.Main())
            }
            DemoButton("Navigate to Main (replace)") {
                navHolder.get().navigateTo(RootDestination.Main(), withReplace = true)
            }
            DemoButton("Start New Flow → Main") {
                navHolder.get().startNewFlow(RootDestination.Main())
            }
        }
    }
}
