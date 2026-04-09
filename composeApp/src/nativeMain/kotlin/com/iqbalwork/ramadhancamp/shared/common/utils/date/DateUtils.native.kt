package com.iqbalwork.ramadhancamp.shared.common.utils.date

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.currentLocale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitDay

actual fun getCurrentDateLocalized(formatPattern: DateFormatPattern): String {
    val formatter = NSDateFormatter()
    formatter.dateFormat = formatPattern.pattern
    formatter.locale = NSLocale.currentLocale
    return formatter.stringFromDate(NSDate())
}

actual fun LocalDate.format(localeCode: String?, formatPattern: DateFormatPattern): String {
    val components = NSDateComponents()
    components.year = this.year.toLong()
    components.month = this.month.number.toLong()
    components.day = this.day.toLong()
    val date = NSCalendar.currentCalendar.dateFromComponents(components) ?: return ""
    val formatter = NSDateFormatter()
    formatter.dateFormat = formatPattern.pattern
    formatter.locale = localeCode?.let { NSLocale(localeIdentifier = it) } ?: NSLocale.currentLocale
    return formatter.stringFromDate(date)
}

actual fun String.toLocalDate(localeCode: String?, formatPattern: DateFormatPattern): LocalDate {
    val formatter = NSDateFormatter()
    formatter.dateFormat = formatPattern.pattern
    formatter.locale = localeCode?.let { NSLocale(localeIdentifier = it) } ?: NSLocale.currentLocale
    val date = formatter.dateFromString(this) ?: throw IllegalArgumentException("Cannot parse date: $this")
    val components = NSCalendar.currentCalendar.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        fromDate = date
    )
    return LocalDate(components.year.toInt(), components.month.toInt(), components.day.toInt())
}
