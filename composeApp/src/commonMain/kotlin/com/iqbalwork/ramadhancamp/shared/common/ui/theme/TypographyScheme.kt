package com.iqbalwork.ramadhancamp.shared.common.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import ramadhancamp.composeapp.generated.resources.Res
import ramadhancamp.composeapp.generated.resources.lpmq_isep_misbah

data class TypographyScheme(
    val quranAyahLarge: TextStyle = TextStyle.Default,
    val quranAyahMedium: TextStyle = TextStyle.Default,
    val quranBasmala: TextStyle = TextStyle.Default,
    val headlineLarge: TextStyle = TextStyle.Default,
    val headlineMedium: TextStyle = TextStyle.Default,
    val headlineSmall: TextStyle = TextStyle.Default,
    val titleMedium: TextStyle = TextStyle.Default,
    val bodyLarge: TextStyle = TextStyle.Default,
    val bodyMedium: TextStyle = TextStyle.Default,
    val translationText: TextStyle = TextStyle.Default,
    val labelLarge: TextStyle = TextStyle.Default,
    val labelSmall: TextStyle = TextStyle.Default,
)

private val lpmqFontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.lpmq_isep_misbah, FontWeight.Normal),
    )

@Composable
fun ramadhanTypography() = TypographyScheme(
    quranAyahLarge = TextStyle(
        fontSize = 28.sp,
        lineHeight = 48.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = lpmqFontFamily,
    ),
    quranAyahMedium = TextStyle(
        fontSize = 22.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = lpmqFontFamily,
    ),
    quranBasmala = TextStyle(
        fontSize = 24.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = lpmqFontFamily,
    ),
    headlineLarge = TextStyle(
        fontSize = 24.sp,
        lineHeight = 29.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = lpmqFontFamily,
    ),
    headlineMedium = TextStyle(
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight(600),
        fontFamily = lpmqFontFamily,
    ),
    headlineSmall = TextStyle(
        fontSize = 17.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight(600),
        fontFamily = lpmqFontFamily,
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = lpmqFontFamily,
    ),
    bodyLarge = TextStyle(
        fontSize = 15.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = lpmqFontFamily,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = lpmqFontFamily,
    ),
    translationText = TextStyle(
        fontSize = 14.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = lpmqFontFamily,
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 17.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = lpmqFontFamily,
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = lpmqFontFamily,
    ),
)
