package com.iqbalwork.ramadhancamp.feature.quran.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.iqbalwork.ramadhancamp.feature.quran.presentation.components.AudioPlayerBar
import com.iqbalwork.ramadhancamp.feature.quran.presentation.components.AyatCard
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranDetailEvent
import com.iqbalwork.ramadhancamp.feature.quran.presentation.model.QuranDetailState
import com.iqbalwork.ramadhancamp.shared.common.extension.rememberViewModel
import com.iqbalwork.ramadhancamp.shared.common.ui.rememberDispatch
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import kotlinx.coroutines.delay
import org.koin.core.parameter.parametersOf
import chaintech.videoplayer.host.MediaPlayerHost
import chaintech.videoplayer.ui.audio.AudioPlayer
import com.iqbalwork.ramadhancamp.shared.common.ui.components.loading.Loader
import com.iqbalwork.ramadhancamp.shared.common.ui.components.snackbar.RamadhanSnackBarHost
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.RamadhanErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.toErrorEmptyState
import com.iqbalwork.ramadhancamp.shared.common.ui.components.error.ErrorEmptyState

private sealed interface QuranDetailAnimState {
    data object Loading : QuranDetailAnimState
    class Error(val error: ErrorEmptyState) : QuranDetailAnimState
    data object Success : QuranDetailAnimState
}

@Composable
fun QuranDetailScreen(params: QuranDetailScreenParameters) {
    val viewModel: QuranDetailViewModel = rememberViewModel { parametersOf(params) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action = viewModel.rememberDispatch()

    RamadhanSnackBarHost(
        modifier = Modifier.fillMaxSize(),
        snackbarFlow = viewModel.snackBarEvents
    ) {
        QuranDetailContent(state = state, action = action, scrollToAyat = params.scrollToAyat, viewModel = viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranDetailContent(state: QuranDetailState, action: (QuranDetailEvent) -> Unit, scrollToAyat: Int? = null, viewModel: QuranDetailViewModel) {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography
    val listState = rememberLazyListState()

    val mediaPlayerHost = viewModel.mediaPlayerHost
    val preBufferHost = remember {
        MediaPlayerHost(mediaUrl = "", autoPlay = false, isLooping = false)
    }

    DisposableEffect(Unit) {
        action(QuranDetailEvent.OnScreenResume)
        onDispose {
            action(QuranDetailEvent.OnScreenDispose)
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        action(QuranDetailEvent.OnAppPause)
    }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        action(QuranDetailEvent.OnAppResume)
    }

    // Pre-load next ayat audio to warm the network cache
    LaunchedEffect(state.nextAyatAudioUrl) {
        val nextUrl = state.nextAyatAudioUrl
        if (nextUrl != null) {
            preBufferHost.loadUrl(nextUrl)
            preBufferHost.pause()
        }
    }

    // Scroll to specific ayat when navigated from search results (only once)
    val scrollTarget = scrollToAyat
    LaunchedEffect(scrollTarget, state.surahDetail) {
        if (!state.hasScrolledToInitialAyah && scrollTarget != null && state.surahDetail != null) {
            val index = state.surahDetail!!.ayat.indexOfFirst { it.nomorAyat == scrollTarget }
            if (index >= 0) {
                delay(300) // wait for layout
                listState.animateScrollToItem(index)
                action(QuranDetailEvent.InitialScrollConsumed)
            }
        }
    }

    // Auto-scroll to active playing ayat (only once per audio change)
    LaunchedEffect(state.playingAyat) {
        if (!state.autoScrolledToPlayingAyat) {
            state.playingAyat?.let { activeAyat ->
                val index = state.surahDetail?.ayat?.indexOf(activeAyat) ?: -1
                if (index != -1) {
                    listState.animateScrollToItem(index)
                    action(QuranDetailEvent.AutoScrollToPlayingAyatConsumed)
                }
            }
        }
    }

    Box(modifier = Modifier.size(0.dp)) {
        AudioPlayer(playerHost = mediaPlayerHost)
    }

    DisposableEffect(preBufferHost) {
        onDispose {
            preBufferHost.pause()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.surahDetail?.namaLatin ?: "Loading...",
                        style = typography.headlineSmall,
                        color = colors.textPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { action(QuranDetailEvent.Back) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.textPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.bgPrimary
                )
            )
        },
        containerColor = colors.bgPrimary
    ) { innerPadding ->
        AnimatedContent(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            targetState = when {
                state.isLoading && state.surahDetail == null -> QuranDetailAnimState.Loading
                state.appError != null -> QuranDetailAnimState.Error(state.appError!!.toErrorEmptyState())
                else -> QuranDetailAnimState.Success
            }
        ) { targetState ->
            when (targetState) {
                is QuranDetailAnimState.Loading -> Loader(modifier = Modifier.fillMaxSize())
                is QuranDetailAnimState.Error -> {
                    RamadhanErrorEmptyState(
                        modifier = Modifier.fillMaxSize(),
                        errorEmptyState = targetState.error,
                        onButtonClick = { action(QuranDetailEvent.Retry) }
                    )
                }
                is QuranDetailAnimState.Success -> Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = if (state.playingAyat != null) 160.dp else 16.dp)
                    ) {
                        val ayatList = state.surahDetail?.ayat ?: emptyList()
                        itemsIndexed(ayatList) { index, ayat ->
                            AyatCard(
                                ayat = ayat,
                                onOptionsClick = { action(QuranDetailEvent.OpenAyatSheet(ayat)) },
                                isActive = (ayat == state.playingAyat),
                                isBookmarked = ayat.nomorAyat in state.bookmarkedAyatNumbers
                            )
                        }
                    }


                    AnimatedVisibility(
                        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
                        visible = state.playingAyat != null,
                        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                    ) {
                        AudioPlayerBar(
                            surahName = state.surahDetail?.namaLatin ?: "",
                            ayatNumber = state.playingAyat?.nomorAyat ?: 0,
                            reciterName = "Misyari Rasyid",
                            isPlaying = state.isPlaying,
                            isBuffering = state.isBuffering,
                            currentTimeMs = state.currentTimeMs,
                            totalDurationMs = state.totalTimeMs,
                            onSeek = { action(QuranDetailEvent.OnSeekAudio(it)) },
                            onPlayPause = { action(QuranDetailEvent.TogglePlayPause) },
                            onNext = { action(QuranDetailEvent.PlayNextAyat) },
                            onPrev = { action(QuranDetailEvent.PlayPrevAyat) },
                            onClose = { action(QuranDetailEvent.StopAudio) },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}
