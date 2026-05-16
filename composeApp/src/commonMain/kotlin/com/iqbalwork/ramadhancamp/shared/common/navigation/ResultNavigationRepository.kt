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
    private val resultsFlow = mutableMapOf<String, MutableSharedFlow<NavigationResult>>()

    fun getResultFlow(key: String): SharedFlow<NavigationResult> {
        return resultsFlow.getOrPut(key) {
            MutableSharedFlow()
        }.asSharedFlow()
    }

    fun sendResult(
        key: String,
        data: NavigationResult,
    ) {
        coroutineScope.launch {
            resultsFlow[key]?.emit(data)
        }
    }

    fun removeKey(key: String) {
        resultsFlow.remove(key)
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
