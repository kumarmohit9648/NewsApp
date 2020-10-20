package com.knovatik.navadesh.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
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

fun Context.loadUrl(url: String) {
    val builder = CustomTabsIntent.Builder();
    val customTabsIntent = builder.build();
    customTabsIntent.launchUrl(this, Uri.parse(url));
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Dismiss") {
            snackbar.dismiss()
        }
    }.show()
}

fun getEncoded64ImageStringFromBitmap(bitmap: Bitmap): String? {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
    val byteFormat = stream.toByteArray()
    return Base64.encodeToString(byteFormat, Base64.NO_WRAP)
}

fun EditText.setErrorWithFocus(message: String) {
    requestFocus()
    error = message
}