package com.example.inventoryapp.util

import android.content.Context
import android.widget.Toast

class MyUtils {
    companion object {
        fun toast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

}