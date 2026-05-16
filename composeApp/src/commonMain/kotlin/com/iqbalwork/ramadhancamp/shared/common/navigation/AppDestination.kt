package com.iqbalwork.ramadhancamp.shared.common.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeMainScreenParameters
import com.iqbalwork.ramadhancamp.feature.pray.presentation.PrayMainScreenParameters
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranDetailScreenParameters
import com.iqbalwork.ramadhancamp.feature.quran.presentation.QuranSheetScreenParameters
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
sealed interface RootDestination : NavKey {
    @Serializable data class Main(
        @Transient val initialTab: FeatureTab? = null,
    ) : RootDestination
    @Serializable data object Auth : RootDestination
}

@Serializable
sealed interface TabDestination : NavKey {
    // Home
    @Serializable data class HomeMain(val param: HomeMainScreenParameters) : TabDestination
    @Serializable data object HomeLocationPicker : TabDestination
    // Pray
    @Serializable data class PrayMain(val param: PrayMainScreenParameters) : TabDestination
    // Quran
    @Serializable data object QuranMain      : TabDestination
    @Serializable data class QuranDetail(val param: QuranDetailScreenParameters) : TabDestination
    // Qibla
    @Serializable data object QiblaMain      : TabDestination
    // Bookmark
    @Serializable data object BookmarkMain      : TabDestination
    @Serializable data object BookmarkDetail    : TabDestination
    @Serializable data object BookmarkSubDetail : TabDestination
}

@Serializable
sealed interface DialogDestination : NavKey {
    @Serializable data class QuranSheet(val param: QuranSheetScreenParameters) : DialogDestination
    @Serializable data object BookmarkSheet : DialogDestination
}

val appSavedStateConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(RootDestination.Main::class,          RootDestination.Main.serializer())
            subclass(RootDestination.Auth::class,          RootDestination.Auth.serializer())
            subclass(TabDestination.HomeMain::class,             TabDestination.HomeMain.serializer())
            subclass(TabDestination.HomeLocationPicker::class,   TabDestination.HomeLocationPicker.serializer())
            subclass(TabDestination.PrayMain::class,          TabDestination.PrayMain.serializer())
            subclass(TabDestination.QuranMain::class,         TabDestination.QuranMain.serializer())
            subclass(TabDestination.QuranDetail::class,       TabDestination.QuranDetail.serializer())
            subclass(TabDestination.QiblaMain::class,         TabDestination.QiblaMain.serializer())
            subclass(TabDestination.BookmarkMain::class,      TabDestination.BookmarkMain.serializer())
            subclass(TabDestination.BookmarkDetail::class,    TabDestination.BookmarkDetail.serializer())
            subclass(TabDestination.BookmarkSubDetail::class, TabDestination.BookmarkSubDetail.serializer())
            subclass(DialogDestination.QuranSheet::class,     DialogDestination.QuranSheet.serializer())
            subclass(DialogDestination.BookmarkSheet::class,  DialogDestination.BookmarkSheet.serializer())
        }
    }
}