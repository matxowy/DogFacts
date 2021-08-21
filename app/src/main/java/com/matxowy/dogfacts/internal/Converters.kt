package com.matxowy.dogfacts.internal

import androidx.room.TypeConverter
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

object Converters {
    @TypeConverter
    @JvmStatic
    fun stringToDate(str: String?) = str?.let {
        ZonedDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(dateTime: ZonedDateTime?) = dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE)
}