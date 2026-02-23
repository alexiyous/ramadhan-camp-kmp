package com.iqbalwork.ramadhancamp.shared.common.ui.utils

import com.iqbalwork.ramadhancamp.shared.common.ui.UiEvent

internal interface HandleEvent<Event: UiEvent> {
    fun handleEvent(event: Event)
}