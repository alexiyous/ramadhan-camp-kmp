package com.iqbalwork.ramadhancamp.feature.bookmark.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.presentation.DemoButton

@Composable
fun BookmarkSheetScreen(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("Bookmark Sheet", style = MaterialTheme.typography.titleMedium)
        Text("showDialog(DialogDestination.BookmarkSheet)", style = MaterialTheme.typography.bodySmall)
        DemoButton("Close (hideDialog)") { onDismiss() }
        Spacer(Modifier.height(8.dp))
    }
}
