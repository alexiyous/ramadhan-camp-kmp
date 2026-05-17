package com.iqbalwork.ramadhancamp.feature.bookmark.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.components.BookmarkCard
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.components.BookmarkSearchBar
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.components.CategoryChip
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkEvent
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.model.BookmarkState
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme

@Composable
fun BookmarkScreen() {
    val viewModel: BookmarkViewModel = rememberViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatch = viewModel.rememberDispatch()

    BookmarkContent(state = state, action = dispatch)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BookmarkContent(
    state: BookmarkState,
    action: (BookmarkEvent) -> Unit
) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.isSearchActive) {
        if (state.isSearchActive) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    // Delete category confirmation dialog
    val categoryToDelete = state.categoryToDelete
    if (categoryToDelete != null) {
        AlertDialog(
            onDismissRequest = { action(BookmarkEvent.DismissDeleteCategory) },
            containerColor = colors.bgSecondary,
            titleContentColor = colors.textPrimary,
            textContentColor = colors.textSecondary,
            title = {
                Text(
                    text = "Delete Category?",
                    style = typography.headlineSmall,
                    color = colors.textPrimary
                )
            },
            text = {
                Text(
                    text = "All saved bookmarks inside this category will be deleted.",
                    style = typography.bodyLarge,
                    color = colors.textSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = { action(BookmarkEvent.ConfirmDeleteCategory) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.accentEmerald,
                        contentColor = colors.textOnLight
                    )
                ) {
                    Text("Delete", style = typography.labelLarge)
                }
            },
            dismissButton = {
                TextButton(onClick = { action(BookmarkEvent.DismissDeleteCategory) }) {
                    Text("Cancel", color = colors.textMuted, style = typography.labelLarge)
                }
            }
        )
    }

    // Delete bookmark confirmation dialog
    if (state.bookmarkToDelete != null) {
        AlertDialog(
            onDismissRequest = { action(BookmarkEvent.DismissDeleteBookmark) },
            title = {
                Text(
                    text = "Hapus Bookmark?",
                    style = typography.headlineSmall,
                    color = colors.textPrimary
                )
            },
            text = {
                Text(
                    text = "Bookmark untuk ayat ini akan dihapus.",
                    style = typography.bodyLarge,
                    color = colors.textSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = { action(BookmarkEvent.ConfirmDeleteBookmark) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.accentPrimary
                    )
                ) {
                    Text("Hapus", color = colors.textOnLight)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { action(BookmarkEvent.DismissDeleteBookmark) }
                ) {
                    Text("Batal", color = colors.textMuted)
                }
            },
            containerColor = colors.bgSecondary,
            titleContentColor = colors.textPrimary,
            textContentColor = colors.textSecondary
        )
    }

    SharedTransitionLayout {
        Scaffold(
            topBar = {
                AnimatedContent(
                    targetState = state.isSearchActive,
                    label = "search_transition"
                ) { isSearchActive ->
                    if (isSearchActive) {
                        BookmarkSearchBar(
                            query = state.searchQuery,
                            onQueryChange = { action(BookmarkEvent.OnSearchQueryChanged(it)) },
                            onBack = {
                                keyboardController?.hide()
                                action(BookmarkEvent.OnSearchCloseClicked)
                            },
                            onClear = {
                                action(BookmarkEvent.OnSearchQueryChanged(""))
                            },
                            focusRequester = focusRequester,
                            modifier = Modifier
                                .fillMaxWidth()
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState(key = "search_bar"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                )
                        )
                    } else {
                        TopAppBar(
                            modifier = Modifier.sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "search_bar"),
                                animatedVisibilityScope = this@AnimatedContent,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ),
                            title = { Text("Ayat Favorit", style = typography.headlineSmall, color = colors.textPrimary) },
                            navigationIcon = {
                                IconButton(onClick = { action(BookmarkEvent.OnBackClicked) }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        tint = colors.textPrimary
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { action(BookmarkEvent.OnSearchClicked) }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Search,
                                        contentDescription = "Search",
                                        tint = colors.textPrimary
                                    )
                                }
                                IconButton(onClick = { action(BookmarkEvent.OnAddBookmarkClick) }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Add,
                                        contentDescription = "Add",
                                        tint = colors.textPrimary
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colors.bgPrimary
                            )
                        )
                    }
                }
            },
            containerColor = colors.bgPrimary
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Category tabs
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            CategoryChip(
                                name = "Semua",
                                isSelected = state.selectedCategoryId == null,
                                onClick = { action(BookmarkEvent.OnCategorySelected(null)) },
                                color = 0xFF4ADE80L
                            )
                        }
                        items(state.categories) { category ->
                            Box {
                                CategoryChip(
                                    name = category.name,
                                    isSelected = state.selectedCategoryId == category.id,
                                    onClick = { action(BookmarkEvent.OnCategorySelected(category.id)) },
                                    onLongClick = { action(BookmarkEvent.OnDeleteCategoryClicked(category)) },
                                    color = category.color
                                )
                            }
                        }
                    }

                    // Bookmark list or empty search results
                    if (state.bookmarks.isEmpty() && state.searchQuery.isNotBlank()) {
                        // Empty search results
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = colors.textMuted,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Tidak ada hasil untuk \"${state.searchQuery}\"",
                                    style = typography.bodyLarge,
                                    color = colors.textSecondary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Coba kata kunci lain",
                                    style = typography.labelSmall,
                                    color = colors.textMuted
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                top = 0.dp,
                                bottom = 16.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.bookmarks) { bookmark ->
                                val category = state.categories.find { it.id == bookmark.categoryId }
                                BookmarkCard(
                                    bookmark = bookmark,
                                    categoryName = category?.name ?: "Unknown",
                                    categoryColor = category?.color ?: 0xFF4ADE80L,
                                    onClick = { action(BookmarkEvent.OnBookmarkClick(bookmark)) },
                                    onBookmarkClick = { action(BookmarkEvent.OnDeleteBookmarkClicked(bookmark)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
