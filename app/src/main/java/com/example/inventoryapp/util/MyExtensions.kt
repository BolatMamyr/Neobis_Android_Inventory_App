package com.example.inventoryapp.util

import android.widget.EditText
import com.example.inventoryapp.R

fun <T : EditText> T.showError() {
    error = context.getString(R.string.error_fill_fields)
    clearFocus()
}