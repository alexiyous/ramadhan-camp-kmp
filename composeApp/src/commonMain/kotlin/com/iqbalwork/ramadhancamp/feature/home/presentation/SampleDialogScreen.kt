package com.iqbalwork.ramadhancamp.feature.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SampleDialogScreen(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Sample Bottom Sheet", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "This bottom sheet is driven by BottomSheetSceneStrategy via the tab backstack.")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onDismiss) {
            Text(text = "Close")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
