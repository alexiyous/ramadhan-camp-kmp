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
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalBackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.RootDestination
import com.iqbalwork.ramadhancamp.shared.common.presentation.DemoButton
import com.iqbalwork.ramadhancamp.shared.common.presentation.DemoSection

@Composable
fun AuthScreen() {
    val rootBackStack = LocalBackStackNode.current.backStack
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
                rootBackStack.add(RootDestination.Main())
            }
            DemoButton("Navigate to Main (replace)") {
                if (rootBackStack.isNotEmpty()) rootBackStack[rootBackStack.lastIndex] = RootDestination.Main()
                else rootBackStack.add(RootDestination.Main())
            }
            DemoButton("Start New Flow → Main") {
                rootBackStack.clear()
                rootBackStack.add(RootDestination.Main())
            }
        }
    }
}
