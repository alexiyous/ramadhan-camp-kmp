package com.iqbalwork.ramadhancamp.shared.common.ui.reducer

data class ReducerResult<State : Any?, Effect : ReducerEffect?>(
    val state: State? = null,
    val effect: Effect? = null,
)
