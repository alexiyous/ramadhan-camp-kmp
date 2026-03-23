package com.iqbalwork.ramadhancamp.shared.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

class TabState(
    private val tabs: List<FeatureTab>,
    private val savedIndex: MutableState<Int>,
    private val _state: MutableState<FeatureTab>,
) {
    val current: FeatureTab get() = _state.value
    val state: State<FeatureTab> get() = _state

    fun select(tab: FeatureTab) {
        _state.value     = tab
        savedIndex.value = tabs.indexOf(tab)
    }
}

@Composable
fun rememberTabState(
    tabs: List<FeatureTab>,
    initialTab: FeatureTab = tabs.first(),
): TabState {
    val savedIndex = rememberSaveable { mutableStateOf(tabs.indexOf(initialTab).coerceAtLeast(0)) }
    val tabState   = remember {
        mutableStateOf(tabs.getOrElse(savedIndex.value) { initialTab })
    }
    return remember { TabState(tabs, savedIndex, tabState) }
}

val LocalCurrentTab = compositionLocalOf<TabState> {
    error("No TabState provided. Wrap your NavDisplay with CompositionLocalProvider(LocalCurrentTab provides tabState)")
}
