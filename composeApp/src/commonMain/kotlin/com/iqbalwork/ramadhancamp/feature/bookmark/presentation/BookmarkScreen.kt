package com.iqbalwork.ramadhancamp.feature.bookmark.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.components.BookmarkCard
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.components.CategoryChip
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkEvent
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkState
import com.iqbalwork.ramadhancamp.shared.common.ui.ScreenContent
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookmarkScreen() {
    val viewModel: BookmarkViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatch = viewModel.rememberDispatch()

    ScreenContent(viewModel) {
        BookmarkContent(state = state, action = dispatch)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkContent(
    state: BookmarkState,
    action: (BookmarkEvent) -> Unit
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bookmarks", style = typography.headlineSmall, color = colors.textPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.bgPrimary
                ),
                actions = {
                    IconButton(onClick = { action(BookmarkEvent.OnAddBookmarkClick) }) {
                        Text("+", color = colors.accentPrimary, style = typography.headlineLarge)
                    }
                }
            )
        },
        containerColor = colors.bgPrimary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colors.bgPrimary)
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { action(BookmarkEvent.OnSearchQueryChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search bookmarks...", color = colors.textMuted) },
                textStyle = typography.bodyLarge.copy(color = colors.textPrimary),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.accentPrimary,
                    unfocusedBorderColor = colors.divider,
                    cursorColor = colors.accentPrimary
                ),
                singleLine = true
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    CategoryChip(
                        name = "All",
                        isSelected = state.selectedCategoryId == null,
                        onClick = { action(BookmarkEvent.OnCategorySelected(null)) }
                    )
                }
                items(state.categories) { category ->
                    CategoryChip(
                        name = category.name,
                        isSelected = state.selectedCategoryId == category.id,
                        onClick = { action(BookmarkEvent.OnCategorySelected(category.id)) }
                    )
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.bookmarks) { bookmark ->
                    BookmarkCard(
                        bookmark = bookmark,
                        categoryName = state.categories.find { it.id == bookmark.categoryId }?.name ?: "Unknown",
                        onClick = { action(BookmarkEvent.OnBookmarkClick(bookmark)) },
                        onPlayClick = { action(BookmarkEvent.OnPlayClick(bookmark)) }
                    )
                }
            }
        }
    }
}
