package com.iqbalwork.ramadhancamp.shared.common.utils.date

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate


@RequiresApi(Build.VERSION_CODES.O)
actual fun getCurrentDateLocalized(formatPattern: DateFormatPattern): String {
    val formatter = DateTimeFormatter.ofPattern(formatPattern.pattern, Locale.getDefault())
    return java.time.LocalDate.now().format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
actual fun LocalDate.format(localeCode: String?, formatPattern: DateFormatPattern): String {
    val locale = localeCode?.let { Locale.forLanguageTag(it) } ?: Locale.getDefault()
    val formatter = DateTimeFormatter.ofPattern(formatPattern.pattern, locale)
    return this.toJavaLocalDate().format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
actual fun String.toLocalDate(localeCode: String?, formatPattern: DateFormatPattern): LocalDate {
    val locale = localeCode?.let { Locale.forLanguageTag(it) } ?: Locale.getDefault()
    val formatter = DateTimeFormatter.ofPattern(formatPattern.pattern, locale)
    return java.time.LocalDate.parse(this, formatter).toKotlinLocalDate()
}
