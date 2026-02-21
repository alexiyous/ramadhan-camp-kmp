package com.iqbalwork.ramadhancamp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.iqbalwork.ramadhancamp.application.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "RamadhanCamp",
    ) {
        App()
    }
}
