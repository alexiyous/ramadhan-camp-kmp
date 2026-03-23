package com.iqbalwork.ramadhancamp.shared.common.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import com.iqbalwork.ramadhancamp.feature.home.presentation.HomeMainScreenParameters
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
    @Serializable data class HomeMain(val param: HomeMainScreenParameters)           : TabDestination
    @Serializable data object HomeDetail         : TabDestination
    @Serializable data object HomeSubDetail      : TabDestination
    @Serializable data object HomeLocationPicker : TabDestination
    // Pray
    @Serializable data object PrayMain       : TabDestination
    @Serializable data object PrayDetail     : TabDestination
    @Serializable data object PraySubDetail  : TabDestination
    // Quran
    @Serializable data object QuranMain      : TabDestination
    @Serializable data object QuranDetail    : TabDestination
    @Serializable data object QuranSubDetail : TabDestination
    // Qibla
    @Serializable data object QiblaMain      : TabDestination
    @Serializable data object QiblaDetail    : TabDestination
    @Serializable data object QiblaSubDetail : TabDestination
    // Bookmark
    @Serializable data object BookmarkMain      : TabDestination
    @Serializable data object BookmarkDetail    : TabDestination
    @Serializable data object BookmarkSubDetail : TabDestination
}

@Serializable
sealed interface DialogDestination : NavKey {
    @Serializable data object HomeSheet     : DialogDestination
    @Serializable data object PraySheet     : DialogDestination
    @Serializable data object QuranSheet    : DialogDestination
    @Serializable data object QiblaSheet    : DialogDestination
    @Serializable data object BookmarkSheet : DialogDestination
}

val appSavedStateConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(RootDestination.Main::class,          RootDestination.Main.serializer())
            subclass(RootDestination.Auth::class,          RootDestination.Auth.serializer())
            subclass(TabDestination.HomeMain::class,             TabDestination.HomeMain.serializer())
            subclass(TabDestination.HomeDetail::class,           TabDestination.HomeDetail.serializer())
            subclass(TabDestination.HomeSubDetail::class,        TabDestination.HomeSubDetail.serializer())
            subclass(TabDestination.HomeLocationPicker::class,   TabDestination.HomeLocationPicker.serializer())
            subclass(TabDestination.PrayMain::class,          TabDestination.PrayMain.serializer())
            subclass(TabDestination.PrayDetail::class,        TabDestination.PrayDetail.serializer())
            subclass(TabDestination.PraySubDetail::class,     TabDestination.PraySubDetail.serializer())
            subclass(TabDestination.QuranMain::class,         TabDestination.QuranMain.serializer())
            subclass(TabDestination.QuranDetail::class,       TabDestination.QuranDetail.serializer())
            subclass(TabDestination.QuranSubDetail::class,    TabDestination.QuranSubDetail.serializer())
            subclass(TabDestination.QiblaMain::class,         TabDestination.QiblaMain.serializer())
            subclass(TabDestination.QiblaDetail::class,       TabDestination.QiblaDetail.serializer())
            subclass(TabDestination.QiblaSubDetail::class,    TabDestination.QiblaSubDetail.serializer())
            subclass(TabDestination.BookmarkMain::class,      TabDestination.BookmarkMain.serializer())
            subclass(TabDestination.BookmarkDetail::class,    TabDestination.BookmarkDetail.serializer())
            subclass(TabDestination.BookmarkSubDetail::class, TabDestination.BookmarkSubDetail.serializer())
            subclass(DialogDestination.HomeSheet::class,      DialogDestination.HomeSheet.serializer())
            subclass(DialogDestination.PraySheet::class,      DialogDestination.PraySheet.serializer())
            subclass(DialogDestination.QuranSheet::class,     DialogDestination.QuranSheet.serializer())
            subclass(DialogDestination.QiblaSheet::class,     DialogDestination.QiblaSheet.serializer())
            subclass(DialogDestination.BookmarkSheet::class,  DialogDestination.BookmarkSheet.serializer())
        }
    }
}
