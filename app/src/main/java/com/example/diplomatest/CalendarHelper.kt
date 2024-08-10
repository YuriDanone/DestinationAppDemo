package com.example.diplomatest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import java.util.*

object CalendarHelper {

    fun addEventToCalendar(
        context: Context,
        title: String,
        description: String,
        location: String,
        startTime: Long,
        endTime: Long
    ) {
        val cr = context.contentResolver
        val contentValues = ContentValues().apply {
            put(CalendarContract.Events.CALENDAR_ID, 1) // 1 for primary calendar
            put(CalendarContract.Events.TITLE, title)
            put(CalendarContract.Events.DESCRIPTION, description)
            put(CalendarContract.Events.EVENT_LOCATION, location)
            put(CalendarContract.Events.DTSTART, startTime)
            put(CalendarContract.Events.DTEND, endTime)
            put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
        }

        val uri: Uri? = cr.insert(CalendarContract.Events.CONTENT_URI, contentValues)
        // Optionally, you can get the event ID from the returned URI
        val eventId: Long = uri?.lastPathSegment?.toLongOrNull() ?: -1
    }
}
