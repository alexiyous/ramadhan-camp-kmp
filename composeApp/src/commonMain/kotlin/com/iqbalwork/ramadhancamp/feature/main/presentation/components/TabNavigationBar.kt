package com.iqbalwork.ramadhancamp.feature.main.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.shared.common.navigation.FeatureTab
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun TabNavigationBar(
    tabs: List<FeatureTab>,
    currentTab: FeatureTab,
    onTabSelected: (FeatureTab) -> Unit,
) {
    val colors = RamadhanTheme.colors
    Column {
        HorizontalDivider(thickness = 0.5.dp, color = colors.divider)
        NavigationBar(
            tonalElevation = 20.dp,
            containerColor = colors.bgPrimary,
            contentColor   = colors.textMuted,
        ) {
            tabs.forEach { tab ->
                val isSelected = currentTab == tab
                val scale by animateFloatAsState(
                    targetValue   = if (isSelected) 1.2f else 1.0f,
                    animationSpec = spring(dampingRatio = 0.5f, stiffness = 300f),
                )
                NavigationBarItem(
                    selected        = isSelected,
                    onClick         = { onTabSelected(tab) },
                    icon            = {
                        Icon(
                            painter            = painterResource(if (isSelected) tab.selectedIcon else tab.unselectedIcon),
                            contentDescription = tab.label,
                            modifier           = Modifier.size(24.dp).scale(scale),
                        )
                    },
                    label           = { Text(tab.label, style = RamadhanTheme.typography.labelSmall) },
                    alwaysShowLabel = true,
                    colors          = NavigationBarItemDefaults.colors(
                        selectedIconColor   = colors.accentPrimary,
                        unselectedIconColor = colors.textMuted,
                        selectedTextColor   = colors.accentPrimary,
                        unselectedTextColor = colors.textMuted,
                        indicatorColor      = Color.Transparent,
                    ),
                )
            }
        }
    }
}
