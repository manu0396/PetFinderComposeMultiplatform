package com.example.petfinder.di

import com.example.data_core.util.CoreConstants
import org.koin.core.logger.Logger
import org.koin.core.logger.Level
import org.koin.core.logger.MESSAGE
import platform.Foundation.NSLog

class IOSKoinLogger(level: Level = Level.INFO) : Logger(level) {
    override fun display(level: Level, msg: MESSAGE) {
        if (level < this.level) return
        val prefix = when (level) {
            Level.DEBUG -> CoreConstants.LogPrefix.DEBUG
            Level.INFO -> CoreConstants.LogPrefix.INFO
            Level.WARNING -> CoreConstants.LogPrefix.WARNING
            Level.ERROR -> CoreConstants.LogPrefix.ERROR
            Level.NONE -> return
        }
        NSLog("%s %s %s", CoreConstants.LogPrefix.KOIN, prefix, msg)
    }
}
