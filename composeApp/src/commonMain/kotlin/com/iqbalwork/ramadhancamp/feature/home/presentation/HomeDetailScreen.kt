package com.iqbalwork.ramadhancamp.feature.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeDetailScreen(viewModel: HomeViewModel = koinViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Home Detail", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Pushed onto the Home tab backstack via navigateToInsideTab()")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { viewModel.back() }) {
            Text(text = "Back")
        }
    }
}
