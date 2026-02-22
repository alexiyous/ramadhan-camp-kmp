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
fun HomeMainScreen(viewModel: HomeViewModel = koinViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Home", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { viewModel.navigateToDetail() }) {
            Text(text = "Go to Detail")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { viewModel.navigateToQuran() }) {
            Text(text = "Switch to Quran")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { viewModel.showSampleDialog() }) {
            Text(text = "Show Bottom Sheet")
        }
    }
}
