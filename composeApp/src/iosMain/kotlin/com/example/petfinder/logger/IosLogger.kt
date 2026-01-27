package com.example.petfinder.logger

import com.example.domain.util.AppLogger

class IosLogger : AppLogger {
    override fun d(tag: String, message: String) { println("[$tag] $message") }
    override fun e(tag: String, message: String, throwable: Throwable?) {
        println("[$tag] ERROR: $message - $throwable")
    }
}
