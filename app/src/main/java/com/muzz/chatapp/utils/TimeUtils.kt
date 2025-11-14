package com.muzz.chatapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatToTimestamp(time: Long): String {
    val sdf = SimpleDateFormat("EEEE HH:mm", Locale.getDefault())
    return sdf.format(Date(time))
}

fun isWithin20Seconds(prev: Long, curr: Long): Boolean {
    return (curr - prev) < 20_000
}

fun isOverAnHour(prev: Long, curr: Long): Boolean {
    return (curr - prev) > 60 * 60 * 1000
}