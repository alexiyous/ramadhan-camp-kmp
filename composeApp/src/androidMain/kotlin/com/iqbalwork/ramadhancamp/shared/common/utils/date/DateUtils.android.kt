package com.iqbalwork.ramadhancamp.shared.common.utils.date

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
actual fun getCurrentDateLocalized(): String {
    val formatter = DateTimeFormatter.ofPattern(DAY_DATE_MONTH_YEAR_FORMAT, Locale.getDefault())
    return LocalDate.now().format(formatter)
}