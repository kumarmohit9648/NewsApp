package com.newsapp.util

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import kotlin.math.abs

fun likeCountFormat(number: String): String {
    val count = number.toLong()
    return when {
        abs(count / 1000000) >= 1 -> {
            (count / 1000000).toString() + "M"
        }
        abs(count / 1000) >= 1 -> {
            (count / 1000).toString() + "K"
        }
        else -> {
            count.toString()
        }
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun String?.isValidEmail(email: String) =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
