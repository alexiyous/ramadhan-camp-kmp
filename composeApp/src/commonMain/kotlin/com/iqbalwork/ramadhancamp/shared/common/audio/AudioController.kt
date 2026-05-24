package com.iqbalwork.ramadhancamp.shared.common.audio

import chaintech.videoplayer.host.MediaPlayerEvent
import chaintech.videoplayer.host.MediaPlayerHost
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class AudioController {

    val mediaPlayerHost = MediaPlayerHost(mediaUrl = "", autoPlay = false, isLooping = false)

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isBuffering = MutableStateFlow(false)
    val isBuffering: StateFlow<Boolean> = _isBuffering.asStateFlow()

    private val _currentTimeMs = MutableStateFlow(0L)
    val currentTimeMs: StateFlow<Long> = _currentTimeMs.asStateFlow()

    private val _totalTimeMs = MutableStateFlow(0L)
    val totalTimeMs: StateFlow<Long> = _totalTimeMs.asStateFlow()

    private val _mediaEnded = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val mediaEnded: SharedFlow<Unit> = _mediaEnded.asSharedFlow()

    private val _lastLoadedUrl = MutableStateFlow<String?>(null)
    val lastLoadedUrl: StateFlow<String?> = _lastLoadedUrl.asStateFlow()

    init {
        setupListeners()
    }

    private fun setupListeners() {
        mediaPlayerHost.onEvent = { event ->
            when (event) {
                is MediaPlayerEvent.PauseChange -> {
                    _isPlaying.value = !event.isPaused
                }
                is MediaPlayerEvent.CurrentTimeChange -> {
                    _currentTimeMs.value = (event.currentTime * 1000f).toLong()
                }
                is MediaPlayerEvent.TotalTimeChange -> {
                    _totalTimeMs.value = (event.totalTime * 1000f).toLong()
                }
                is MediaPlayerEvent.BufferChange -> {
                    _isBuffering.value = event.isBuffering
                }
                is MediaPlayerEvent.MediaEnd -> {
                    _mediaEnded.tryEmit(Unit)
                }
                else -> {}
            }
        }
        mediaPlayerHost.onError = { _ ->
            // Error handled by individual ViewModels via their own error handling
        }
    }

    fun loadUrl(url: String) {
        _lastLoadedUrl.value = url
        mediaPlayerHost.loadUrl(url)
    }

    fun play() {
        mediaPlayerHost.play()
    }

    fun pause() {
        mediaPlayerHost.pause()
    }

    fun seekTo(positionMs: Long) {
        mediaPlayerHost.seekTo(positionMs / 1000f)
    }

    fun seekToZero() {
        mediaPlayerHost.seekTo(0f)
    }

    fun stop() {
        mediaPlayerHost.pause()
        mediaPlayerHost.seekTo(0f)
        _currentTimeMs.value = 0L
    }

    fun reset() {
        stop()
        _isBuffering.value = false
        _totalTimeMs.value = 0L
        _isPlaying.value = false
    }
}