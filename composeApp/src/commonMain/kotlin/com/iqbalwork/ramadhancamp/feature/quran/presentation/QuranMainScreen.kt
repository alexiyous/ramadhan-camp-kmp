package com.iqbalwork.ramadhancamp.feature.quran.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalwork.ramadhancamp.feature.quran.domain.model.AyatSearchResult
import com.iqbalwork.ramadhancamp.feature.quran.domain.model.SurahSearchResult
import com.iqbalwork.ramadhancamp.feature.quran.presentation.components.AyatSearchResultItem
import com.iqbalwork.ramadhancamp.feature.quran.presentation.components.QuranSearchBar
import com.iqbalwork.ramadhancamp.feature.quran.presentation.components.SurahListItem
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranMainEvent
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranMainState
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.ErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.RamadhanErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.toErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.loading.Loader
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme

private sealed interface AnimateContentState {
    data object Loading : AnimateContentState
    class Error(val error: ErrorEmptyState) : AnimateContentState
    data object Success : AnimateContentState
}

@Composable
fun QuranMainScreen() {
    val viewModel: QuranMainViewModel = rememberViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    QuranMainContent(state = state, action = viewModel.rememberDispatch())
}

@Composable
private fun QuranMainContent(
    state: QuranMainState,
    action: (QuranMainEvent) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(state.focusSearch) {
        if (state.focusSearch) {
            focusRequester.requestFocus()
            action(QuranMainEvent.FocusSearchConsumed)
        }
    }

    Scaffold(
        containerColor = RamadhanTheme.colors.bgPrimary,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets(bottom = innerPadding.calculateBottomPadding()))
        ) {
            Column(
                modifier = Modifier
                    .background(RamadhanTheme.colors.bgPrimary)
                    .padding(top = innerPadding.calculateTopPadding())
            ) {
                Text(
                    text = "Al-Quran",
                    style = RamadhanTheme.typography.headlineLarge,
                    color = RamadhanTheme.colors.textPrimary,
                    modifier = Modifier.padding(16.dp)
                )
                QuranSearchBar(
                    query = state.searchQuery,
                    onQueryChange = { action(QuranMainEvent.Search(it)) },
                    onClear = { action(QuranMainEvent.Search("")) },
                    focusRequester = focusRequester,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = when {
                    state.isLoading && state.surahs.isEmpty() && state.searchResults == null -> AnimateContentState.Loading // Initial loading
                    state.appError != null -> AnimateContentState.Error(state.appError.toErrorEmptyState())
                    else -> AnimateContentState.Success
                },
                label = "QuranMainContent"
            ) { targetState ->
                when (targetState) {
                    is AnimateContentState.Loading -> Loader(modifier = Modifier.fillMaxSize())
                    is AnimateContentState.Error -> {
                        RamadhanErrorEmptyState(
                            modifier = Modifier.fillMaxSize(),
                            errorEmptyState = targetState.error,
                            onButtonClick = { action(QuranMainEvent.Retry) }
                        )
                    }
                    is AnimateContentState.Success -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            if (state.searchResults != null) {
                                items(state.searchResults!!, key = { it.itemKey }) { result ->
                                    when (result) {
                                        is SurahSearchResult -> SurahListItem(
                                            surah = result.surah,
                                            onClick = { action(QuranMainEvent.SurahClicked(result.surah.number)) }
                                        )
                                        is AyatSearchResult -> AyatSearchResultItem(
                                            result = result,
                                            onClick = { action(QuranMainEvent.AyatClicked(result.surahNumber, result.ayat.nomorAyat)) }
                                        )
                                    }
                                    HorizontalDivider(color = RamadhanTheme.colors.divider, thickness = 1.dp)
                                }
                            } else {
                                items(state.surahs, key = { it.number }) { surah ->
                                    SurahListItem(
                                        surah = surah,
                                        onClick = { action(QuranMainEvent.SurahClicked(surah.number)) }
                                    )
                                    HorizontalDivider(color = RamadhanTheme.colors.divider, thickness = 1.dp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

