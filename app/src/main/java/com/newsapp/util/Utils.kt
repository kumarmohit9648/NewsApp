package com.newsapp.util

import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
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

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}