package com.kaleiczyk.model.utils

import java.text.DecimalFormat

fun Double.roundToTwoDecimals(): Double {
    val decimalFormat = DecimalFormat("#.##")
    return decimalFormat.format(this).toDouble()
}
