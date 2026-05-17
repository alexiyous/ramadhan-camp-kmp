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
    val bgSurfaceLight: Color,
    // Accent
    val accentPrimary: Color,
    val accentSecondary: Color,
    val accentEmerald: Color,
    val accentMint: Color,
    val accentTeal: Color,
    val accentGold: Color,
    // Text
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textMuted: Color,
    val textOnLight: Color,
    // Borders
    val divider: Color,
    val borderLight: Color,
    val borderMint: Color,
    val dividerLightTeal: Color,
    // Status
    val colorDanger: Color,
    val colorSuccess: Color,
    val colorInfo: Color,
    // Category
    val categoryPurple: Color,
    val categoryAmber: Color,
    val categoryRose: Color,
    val categorySky: Color,
    val categoryGoldBright: Color,
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
    accentSecondary = accentSecondary,
    accentEmerald = accentEmerald,
    accentMint = accentMint,
    accentTeal = accentTeal,
    accentGold = accentGold,
    textPrimary = textPrimary,
    textSecondary = textSecondary,
    textTertiary = textTertiary,
    textMuted = textMuted,
    textOnLight = textOnLight,
    divider = divider,
    borderLight = borderLight,
    borderMint = borderMint,
    dividerLightTeal = dividerLightTeal,
    colorDanger = colorDanger,
    colorSuccess = colorSuccess,
    colorInfo = colorInfo,
    categoryPurple = categoryPurple,
    categoryAmber = categoryAmber,
    categoryRose = categoryRose,
    categorySky = categorySky,
    categoryGoldBright = categoryGoldBright,
    bgSurfaceLight = bgSurfaceLight
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
    accentSecondary = accentSecondary,
    accentEmerald = accentEmerald,
    accentMint = accentMint,
    accentTeal = accentTeal,
    accentGold = accentGold,
    textPrimary = textOnLight,
    textSecondary = textSecondary,
    textTertiary = textTertiary,
    textMuted = textMuted,
    textOnLight = textOnLight,
    divider = borderLight,
    borderLight = borderLight,
    borderMint = borderMint,
    dividerLightTeal = dividerLightTeal,
    colorDanger = colorDanger,
    colorSuccess = colorSuccess,
    colorInfo = colorInfo,
    categoryPurple = categoryPurple,
    categoryAmber = categoryAmber,
    categoryRose = categoryRose,
    categorySky = categorySky,
    categoryGoldBright = categoryGoldBright,
    bgSurfaceLight = bgSurfaceLight
)

internal val NotSetColorScheme = LightColorScheme.copy()
