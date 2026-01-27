package com.example.petfinder.logger

import com.example.domain.util.AppLogger

class AndroidAppLogger : AppLogger {
    override fun d(tag: String, message: String) { android.util.Log.d(tag, message) }
    override fun e(tag: String, message: String, throwable: Throwable?) {
        android.util.Log.e(tag, message, throwable)
    }
}
