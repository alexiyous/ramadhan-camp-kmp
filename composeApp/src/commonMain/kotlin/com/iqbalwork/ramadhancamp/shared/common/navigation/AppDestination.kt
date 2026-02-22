package com.iqbalwork.ramadhancamp.shared.common.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

enum class AppTab { Home, Pray, Quran, Qibla, Bookmark }

// Root Level
@Serializable
sealed interface RootDestination : NavKey {
    @Serializable
    data class Main(val initialTab: AppTab = AppTab.Home) : RootDestination

    @Serializable
    data object Auth : RootDestination
}

// Nested Level
@Serializable
sealed interface TabDestination : NavKey {

    // Home Level
    @Serializable
    data object HomeMain : TabDestination

    // Pray Level
    @Serializable
    data object PrayMain : TabDestination

    // Quran Level
    @Serializable
    data object QuranMain : TabDestination

    // Qibla Level
    @Serializable
    data object QiblaMain : TabDestination

    // Bookmark Level
    @Serializable
    data object BookmarkMain : TabDestination

    // Home Detail
    @Serializable
    data object HomeDetail : TabDestination
}

// Dialog Navigation (bottom sheet, dialog, etc.)
@Serializable
sealed interface DialogDestination: NavKey {

    @Serializable
    data object SampleDialog : DialogDestination

}

val appSavedStateConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(RootDestination.Main::class, RootDestination.Main.serializer())
            subclass(RootDestination.Auth::class, RootDestination.Auth.serializer())
            subclass(TabDestination.HomeMain::class, TabDestination.HomeMain.serializer())
            subclass(TabDestination.PrayMain::class, TabDestination.PrayMain.serializer())
            subclass(TabDestination.QuranMain::class, TabDestination.QuranMain.serializer())
            subclass(TabDestination.QiblaMain::class, TabDestination.QiblaMain.serializer())
            subclass(TabDestination.BookmarkMain::class, TabDestination.BookmarkMain.serializer())
            subclass(TabDestination.HomeDetail::class, TabDestination.HomeDetail.serializer())
            subclass(DialogDestination.SampleDialog::class, DialogDestination.SampleDialog.serializer())
        }
    }
}


