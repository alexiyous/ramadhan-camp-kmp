package com.iqbalwork.ramadhancamp.shared.common.ui.theme

import androidx.compose.ui.graphics.Color

data class RamadhanColorScheme(
    // Backgrounds
    val bgPrimary: Color,
    val bgSecondary: Color,
    val bgDeep: Color,
    val bgSurface: Color,
    val bgContent: Color,
    val bgContentSecondary: Color,
    val bgAccentLight: Color,
    // Accent
    val accentPrimary: Color,
    val accentEmerald: Color,
    val accentMint: Color,
    val accentTeal: Color,
    // Text
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textMuted: Color,
    val textOnLight: Color,
    // Borders
    val divider: Color,
    val borderLight: Color,
    // Status
    val colorDanger: Color,
    val colorSuccess: Color,
    val colorInfo: Color,
)

internal val DarkColorScheme = RamadhanColorScheme(
    bgPrimary = bgPrimary,
    bgSecondary = bgSecondary,
    bgDeep = bgDeep,
    bgSurface = bgSurface,
    bgContent = bgContent,
    bgContentSecondary = bgContentSecondary,
    bgAccentLight = bgAccentLight,
    accentPrimary = accentPrimary,
    accentEmerald = accentEmerald,
    accentMint = accentMint,
    accentTeal = accentTeal,
    textPrimary = textPrimary,
    textSecondary = textSecondary,
    textTertiary = textTertiary,
    textMuted = textMuted,
    textOnLight = textOnLight,
    divider = divider,
    borderLight = borderLight,
    colorDanger = colorDanger,
    colorSuccess = colorSuccess,
    colorInfo = colorInfo,
)

internal val LightColorScheme = RamadhanColorScheme(
    bgPrimary = bgContent,
    bgSecondary = bgContentSecondary,
    bgDeep = bgContentSecondary,
    bgSurface = bgAccentLight,
    bgContent = bgPrimary,
    bgContentSecondary = bgSecondary,
    bgAccentLight = bgAccentLight,
    accentPrimary = accentPrimary,
    accentEmerald = accentEmerald,
    accentMint = accentMint,
    accentTeal = accentTeal,
    textPrimary = textOnLight,
    textSecondary = Color(0xFF162E26).copy(alpha = 0.75f),
    textTertiary = Color(0xFF162E26).copy(alpha = 0.50f),
    textMuted = textMuted,
    textOnLight = textOnLight,
    divider = borderLight,
    borderLight = borderLight,
    colorDanger = colorDanger,
    colorSuccess = colorSuccess,
    colorInfo = colorInfo,
)

internal val NotSetColorScheme = LightColorScheme.copy()
