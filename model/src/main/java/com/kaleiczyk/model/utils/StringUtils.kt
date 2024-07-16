package com.kaleiczyk.model.utils

fun String.isValidAmount(): Boolean {
    return if (REGEX_FOR_AMOUNT.matches(this)) {
        try {
            toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    } else {
        false
    }
}

private val REGEX_FOR_AMOUNT = Regex("^\\d+(\\.\\d{0,2})?$")
