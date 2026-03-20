package com.iqbalwork.ramadhancamp.shared.common.utils.date

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual fun getCurrentDateLocalized(): String {
    val formatter = NSDateFormatter()
    formatter.dateFormat = DAY_DATE_MONTH_YEAR_FORMAT
    formatter.locale = NSLocale.currentLocale
    return formatter.stringFromDate(NSDate())
}