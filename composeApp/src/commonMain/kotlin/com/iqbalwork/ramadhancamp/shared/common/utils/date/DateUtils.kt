package com.iqbalwork.ramadhancamp.shared.common.utils.date

import kotlinx.datetime.LocalDate

enum class DateFormatPattern(val pattern: String) {
    DAY_DATE_MONTH_YEAR("EEEE, d MMMM yyyy"), // e.g. "Tuesday, 14 October 2026"
    SHORT_DAY_DATE_MONTH_YEAR("EEE, d MMM yyyy"), // e.g. "Tue, 14 Oct 2026"
}

expect fun getCurrentDateLocalized(formatPattern: DateFormatPattern = DateFormatPattern.DAY_DATE_MONTH_YEAR): String

expect fun LocalDate.format(localeCode: String? = null, formatPattern: DateFormatPattern): String

expect fun String.toLocalDate(localeCode: String? = null, formatPattern: DateFormatPattern): LocalDate
