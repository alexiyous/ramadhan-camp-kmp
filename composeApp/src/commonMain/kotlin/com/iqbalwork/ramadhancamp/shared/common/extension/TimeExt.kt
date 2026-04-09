package com.iqbalwork.ramadhancamp.shared.common.extension

/**
 * Converts a time string in the format "HH:mm" to the total number of minutes.
 *
 * @receiver String in "HH:mm" format, where HH is hours and mm is minutes.
 * @return Total minutes as Int.
 * @throws NumberFormatException if the string is not properly formatted.
 */

fun String.toMinutes(): Int {
    val (h, m) = split(":").map { it.toInt() }
    return h * 60 + m
}

/**
 * Converts a time string in the format "HH:mm" to the total number of seconds.
 *
 * @receiver String in "HH:mm" format, where HH is hours and mm is minutes.
 * @return Total seconds as Int.
 * @throws NumberFormatException if the string is not properly formatted.
 */
fun String.toSeconds(): Int {
    val parts = split(":")
    return parts[0].toInt() * 3600 + parts[1].toInt() * 60
}

