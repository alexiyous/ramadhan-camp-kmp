package com.iqbalwork.ramadhancamp.shared.common.utils.date

/**
 * Standard Indonesian Gregorian format: "Selasa, 24 Maret 2024"
 */
const val DAY_DATE_MONTH_YEAR_FORMAT = "EEEE, d MMMM yyyy"

expect fun getCurrentDateLocalized(): String
