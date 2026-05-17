package com.iqbalwork.ramadhancamp.feature.bookmark.domain.model

/**
 * Curated palette of category colors for auto-assignment.
 * Colors are chosen for contrast on the dark theme (#10221D background).
 * New categories are assigned colors round-robin from this palette.
 */
object CategoryColorPalette {
    private val colors = listOf(
        0xFF4ADE80L,  // Emerald — accentEmerald
        0xFFB47AFFL,  // Purple — categoryPurple (adjusted)
        0xFFF59E0BL,  // Amber — categoryAmber
        0xFF26A69AL,  // Teal — accentTeal
        0xFFFB7185L,  // Rose — categoryRose (new)
        0xFF38BDF8L,  // Sky — categorySky (new)
        0xFFFBBF24L,  // Gold Bright — categoryGoldBright (new)
        0xFF34D399L,  // Mint — accentMint
    )

    /** Returns the color for a given index, cycling through the palette. */
    fun colorForIndex(index: Int): Long = colors[index % colors.size]

    /** All palette colors as Long values. */
    fun allColors(): List<Long> = colors.toList()
}
