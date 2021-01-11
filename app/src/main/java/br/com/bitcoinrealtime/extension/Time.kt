package br.com.bitcoinrealtime.extension

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Long.millisToSeconds(): Float = this / 1000F
fun Float.printTwoDecimals(): String = "%.2f".format(this)
fun Long.printMillisAsSeconds(): String = millisToSeconds().printTwoDecimals()

val PT_BR_LOCALE: Locale = Locale.forLanguageTag("pt-BR")
const val PATTERN_FULL_DATETIME = "dd MMM yyyy, HH:mm"

val dateFormat = SimpleDateFormat(
    PATTERN_FULL_DATETIME,
    PT_BR_LOCALE
)

fun Long.dayMothYearCommaTime(): String = SimpleDateFormat(
    PATTERN_FULL_DATETIME,
    PT_BR_LOCALE
).format(this)

fun SimpleDateFormat.setSecondMillennium(): SimpleDateFormat {
    val aCalendar = Calendar.getInstance(PT_BR_LOCALE)
    aCalendar.set(2020, 0, 0)
    set2DigitYearStart(aCalendar.time)
    return this
}

fun millisSinceMidnight(): Long {
    val calendar = Calendar.getInstance(PT_BR_LOCALE) // today
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return System.currentTimeMillis() - calendar.timeInMillis
}
