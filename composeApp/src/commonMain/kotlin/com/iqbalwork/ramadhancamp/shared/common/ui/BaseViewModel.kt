package com.iqbalwork.ramadhancamp.shared.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResult
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationResultData
import com.iqbalwork.ramadhancamp.shared.common.ui.components.snackbar.SnackBarData
import com.iqbalwork.ramadhancamp.shared.common.ui.reducer.Reducer
import com.iqbalwork.ramadhancamp.shared.common.ui.reducer.ReducerEffect
import com.iqbalwork.ramadhancamp.shared.common.ui.reducer.ReducerEvent
import com.iqbalwork.ramadhancamp.shared.common.ui.utils.HandleEvent
import com.iqbalwork.ramadhancamp.shared.utils.TAG_LIVECYCLE_VM
import com.iqbalwork.ramadhancamp.shared.utils.TAG_NAVIGATION_RESULT_CANCEL
import com.iqbalwork.ramadhancamp.shared.utils.TAG_NAVIGATION_RESULT_SUCCESS
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<Params: UiParams, State : Any, Event : UiEvent, Effect : UiEffect>(
    protected val params: Params,
    initialState: State,
    protected val navigationManager: AppNavigationController,
    private vararg val resultKeys: String = emptyArray(),
) : ViewModel(), HandleEvent<Event> {

    private val mutableState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = mutableState.asStateFlow()

    init {
        Napier.d(tag = TAG_LIVECYCLE_VM) {
            "init $this"
        }
        resultKeys.forEach { key ->
            viewModelScope.launch {
                navigationManager.subscribeToResult(key)
                    .collect {
                        handleNavigationResult(it)
                    }
            }
        }
    }

    private val _effects = MutableSharedFlow<Effect>()
    val effects = _effects.asSharedFlow()

    private val _snackBarEvents = Channel<SnackBarData>(Channel.BUFFERED)
    val snackBarEvents = _snackBarEvents.receiveAsFlow()

    protected fun updateState(update: State.() -> State) {
        mutableState.update(update)
    }

    protected fun updateStateWith(update: (State) -> State) {
        mutableState.update { currentState ->
            update(currentState)
        }
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effects.emit(effect) }
    }

    protected fun showSnackBar(
        data: SnackBarData
    ) {
        viewModelScope.launch {
            _snackBarEvents.send(data)
        }
    }

    override fun onCleared() {
        Napier.d(tag = TAG_LIVECYCLE_VM) {
            "onDispose $this"
        }
        resultKeys.forEach { key ->
            navigationManager.removeKey(key)
        }
        super.onCleared()
    }

    protected open fun navigationResultSuccess(
        key: String,
        data: NavigationResultData?,
    ) {
        Napier.d(tag = TAG_NAVIGATION_RESULT_SUCCESS) {
            "$this key = $key data = $data"
        }
    }

    protected open fun navigationResultCancel(key: String) {
        Napier.d(tag = TAG_NAVIGATION_RESULT_CANCEL) {
            "$this key = $key"
        }
    }

    private fun handleNavigationResult(result: NavigationResult) {
        when (result) {
            is NavigationResult.Cancel -> {
                navigationResultCancel(key = result.key)
            }
            is NavigationResult.Success -> {
                navigationResultSuccess(
                    key = result.key,
                    data = result.value,
                )
            }
        }
    }

    fun <ReducerEffect_ : ReducerEffect, Event : ReducerEvent> Event.handledBy(
        reducer: Reducer<State, Event, ReducerEffect_>,
        onEffect: (ReducerEffect_) -> Unit,
    ) {
        updateState {
            val (newState, effect) = reducer(state = this, event = this@handledBy)
            if (effect != null) onEffect(effect)
            newState ?: this
        }
    }
}

@Composable
internal fun <Params: UiParams, State : Any, Event : UiEvent, Effect : UiEffect> ScreenContent(
    viewModel: BaseViewModel<Params, State, Event, Effect>,
    screenContent: @Composable BaseViewModel<Params, State, Event, Effect>.() -> Unit
) = screenContent.invoke(viewModel)

@Composable
internal fun <Event : UiEvent> BaseViewModel<*, *, Event, *>.rememberDispatch(): (Event) -> Unit {
    return remember { { event: Event -> this.handleEvent(event) } }
}

@Composable
internal fun <Effect : UiEffect> BaseViewModel<*, *, *, Effect>.UiEffect(
    collector: (uiEffect: Effect) -> Unit
) {
    LaunchedEffect(effects) {
        effects.collect { effect ->
            collector.invoke(effect)
        }
    }
}