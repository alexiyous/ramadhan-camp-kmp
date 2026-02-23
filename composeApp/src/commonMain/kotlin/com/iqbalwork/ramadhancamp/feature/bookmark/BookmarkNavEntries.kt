package com.iqbalwork.ramadhancamp.feature.bookmark

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.BookmarkDetailScreen
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.BookmarkMainScreen
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.BookmarkSheetScreen
import com.iqbalwork.ramadhancamp.feature.bookmark.presentation.BookmarkSubDetailScreen
import com.iqbalwork.ramadhancamp.shared.common.bottomSheet.BottomSheetSceneStrategy
import com.iqbalwork.ramadhancamp.shared.common.navigation.AppNavigationController
import com.iqbalwork.ramadhancamp.shared.common.navigation.DialogDestination
import com.iqbalwork.ramadhancamp.shared.common.navigation.TabDestination

fun EntryProviderBuilder<NavKey>.bookmarkTabEntries(nav: AppNavigationController) {
    entry<TabDestination.BookmarkMain>      { BookmarkMainScreen() }
    entry<TabDestination.BookmarkDetail>    { BookmarkDetailScreen() }
    entry<TabDestination.BookmarkSubDetail> { BookmarkSubDetailScreen() }
    entry<DialogDestination.BookmarkSheet>(metadata = BottomSheetSceneStrategy.bottomSheet()) {
        BookmarkSheetScreen(onDismiss = { nav.hideDialog() })
    }
}
