package com.iqbalwork.ramadhancamp.shared.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp

val isPreview @Composable get() = LocalInspectionMode.current

val LocalColors = staticCompositionLocalOf<RamadhanColorScheme> { NotSetColorScheme }

val LocalTypography = staticCompositionLocalOf<TypographyScheme> { TypographyScheme() }

val LocalBottomNavigationPadding = staticCompositionLocalOf { 0.dp }

@Composable
fun RamadhanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val typography = ramadhanTypography()

    CompositionLocalProvider(
        LocalColors provides colorScheme,
        LocalTypography provides typography,
        content = content,
    )
}

object RamadhanTheme {

    val colors: RamadhanColorScheme
        @Composable
        get() = LocalColors.current.also {
            if (it === NotSetColorScheme && isPreview.not())
                error("Colors not provided")
        }

    val typography: TypographyScheme
        @Composable
        get() = LocalTypography.current
}
