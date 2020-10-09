package com.iwahara.antenna.ktor

import org.joda.time.DateTime

interface Clock {
    fun now(): DateTime
}

class ClockNow : Clock {
    override fun now(): DateTime {
        return DateTime.now()
    }
}

class ClockSpecify(private val targetDateTime: DateTime) : Clock {
    override fun now(): DateTime {
        return targetDateTime
    }
}