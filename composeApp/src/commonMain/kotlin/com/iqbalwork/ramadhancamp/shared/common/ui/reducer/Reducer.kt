package com.iqbalwork.ramadhancamp.shared.common.ui.reducer

fun interface Reducer<State : Any, Event : ReducerEvent, Effect : ReducerEffect> {
    operator fun invoke(
        state: State,
        event: Event,
    ): ReducerResult<State?, Effect?>
}