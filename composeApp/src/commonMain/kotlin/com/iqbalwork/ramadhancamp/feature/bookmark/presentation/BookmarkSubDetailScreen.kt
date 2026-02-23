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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.presentation.DemoButton
import com.iqbalwork.ramadhancamp.shared.common.presentation.DemoSection
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookmarkSubDetailScreen(viewModel: BookmarkViewModel = koinViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text("Bookmark SubDetail", style = MaterialTheme.typography.headlineMedium)
        DemoSection("Back to Root") {
            DemoButton("Back to Bookmark Main")             { viewModel.backToMain() }
            DemoButton("Back to Bookmark Main with Result") { viewModel.backToMainWithResult() }
        }
        DemoSection("Back") {
            DemoButton("Back one step") { viewModel.back() }
        }
    }
}
