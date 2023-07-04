package com.itzcafe.creativeiq.utils

import android.content.Context

class SharedPref(var context: Context) {

    fun save(key: String?, value: Int) {
        val editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        editor.putInt(key, value)
        editor.commit()
    }

    operator fun get(key: String?): Int {
        val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, -1)
    }
}