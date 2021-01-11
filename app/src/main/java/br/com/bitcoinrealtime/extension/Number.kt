package br.com.bitcoinrealtime.extension

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

var formatter: NumberFormat = DecimalFormat("#,###")

fun Int.formattedDotNumber(): String = formatter.format(this).replace(',', '.')

fun Long.formattedDotNumber(): String = formatter.format(this).replace(',', '.')

fun Long.formatMoney(): String = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)

fun Long.formatMoneyWithoutSymbol(): String = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this / 100.0).replace("R$", "")
