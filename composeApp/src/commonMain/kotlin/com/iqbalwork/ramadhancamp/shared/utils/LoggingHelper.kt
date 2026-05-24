package com.iqbalwork.ramadhancamp.shared.utils

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

const val TAG_LIVECYCLE_VM = "LIVECYCLE_VM"
const val TAG_NAVIGATION_RESULT_SUCCESS = "NAVIGATION_RESULT_SUCCESS"
const val TAG_NAVIGATION_RESULT_CANCEL = "NAVIGATION_RESULT_CANCEL"
const val TAG_AUTH_STATE = "AUTH_STATE"
const val TAG_DEEPLINK = "DEEPLINK"
const val TAG_PUSH = "PUSH"
const val TAG_BOOKMARK_FTS = "BOOKMARK_FTS"
const val TAG_AUDIO_CHECKPOINT = "AUDIO_CHECKPOINT"

fun initLogging() {
    Napier.base(DebugAntilog())
}
