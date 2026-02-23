package com.iqbalwork.ramadhancamp.feature.bookmark.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.presentation.DemoButton
import com.iqbalwork.ramadhancamp.shared.common.presentation.DemoSection
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookmarkMainScreen(viewModel: BookmarkViewModel = koinViewModel()) {
    val lastResult by viewModel.lastResult.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("Bookmark", style = MaterialTheme.typography.headlineMedium)
        lastResult?.let {
            Text(
                text = "Last result: $it",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        DemoSection("In-Tab") {
            DemoButton("Push Detail")          { viewModel.navigateToDetail() }
            DemoButton("Replace with Detail")  { viewModel.replaceWithDetail() }
        }
        DemoSection("Cross-Tab") {
            DemoButton("Switch to Home") { viewModel.switchToHome() }
        }
        DemoSection("Sheet") {
            DemoButton("Show Bookmark Sheet") { viewModel.showBookmarkSheet() }
        }
    }
}
