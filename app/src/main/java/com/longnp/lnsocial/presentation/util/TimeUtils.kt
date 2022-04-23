package com.longnp.lnsocial.presentation.util

import java.util.concurrent.TimeUnit

object TimeUtils {

    fun convertTimeToDisplayTime(timeInMillis: Long): String {
        return String.format(
            "%d:%d",
            TimeUnit.MILLISECONDS.toMinutes(timeInMillis),
            TimeUnit.MILLISECONDS.toSeconds(timeInMillis) -
                    TimeUnit.MINUTES.toSeconds((TimeUnit.MILLISECONDS.toMinutes(timeInMillis)))
        )
    }

}