package com.iqbalwork.ramadhancamp.feature.main.presentation.utils

import com.iqbalwork.ramadhancamp.shared.common.navigation.FeatureTab

internal fun requireTabInList(tabs: List<FeatureTab>, tab: FeatureTab) {
    require(tab in tabs) {
        "FeatureTab '${tab.label}' is not registered in this screen. " +
            "Registered tabs: [${tabs.joinToString { "'${it.label}'" }}]"
    }
}
