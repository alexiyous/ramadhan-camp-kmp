package com.iqbalwork.ramadhancamp.shared.common.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface NavigationResultData {
    companion object {
        val EMPTY = object : NavigationResultData {}
    }
}

sealed class NavigationResult(val key: String) {
    class Success(key: String, val value: NavigationResultData) : NavigationResult(key)

    class Cancel(key: String) : NavigationResult(key)
}

class ResultNavigationRepository(
    private val coroutineScope: CoroutineScope,
) {
    private data class FlowEntry(
        val flow: MutableSharedFlow<NavigationResult>,
        var refCount: Int = 0
    )

    private val resultsFlow = mutableMapOf<String, FlowEntry>()

    /**
     * Returns the SharedFlow for the given key, creating one if it doesn't exist.
     * Increments the reference count to track active subscribers.
     */
    fun getResultFlow(key: String): SharedFlow<NavigationResult> {
        val entry = resultsFlow.getOrPut(key) { FlowEntry(MutableSharedFlow()) }
        entry.refCount++
        return entry.flow.asSharedFlow()
    }

    fun sendResult(
        key: String,
        data: NavigationResult,
    ) {
        coroutineScope.launch {
            resultsFlow[key]?.flow?.emit(data)
        }
    }

    /**
     * Decrements the reference count for the given key.
     * Removes the flow from the map only when refCount reaches zero,
     * meaning no ViewModel is actively collecting from it anymore.
     * This prevents the race condition where a new subscriber's collector
     * is orphaned when the old subscriber's onCleared removes the shared flow.
     */
    fun releaseKey(key: String) {
        val entry = resultsFlow[key] ?: return
        entry.refCount--
        if (entry.refCount <= 0) {
            resultsFlow.remove(key)
        }
    }
}

data class TextResult(val text: String) : NavigationResultData

data class LastSurahNavigationData(
    val surahId: Int,
    val ayatNumber: Int
) : NavigationResultData


data class SurahNavigationData(
    val surahId: Int
) : NavigationResultData


data class AyatNumberResult(val ayatNumber: Int) : NavigationResultData
