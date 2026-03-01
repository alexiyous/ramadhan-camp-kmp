package com.iqbalwork.ramadhancamp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.iqbalwork.ramadhancamp.shared.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "RamadhanCamp",
        ) {
            App()
        }
    }
}