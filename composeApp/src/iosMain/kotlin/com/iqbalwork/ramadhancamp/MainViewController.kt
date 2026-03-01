package com.iqbalwork.ramadhancamp

import androidx.compose.ui.window.ComposeUIViewController
import com.iqbalwork.ramadhancamp.shared.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    initKoin()
    return ComposeUIViewController { App() }
}