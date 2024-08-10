package com.example.diplomatest

import android.content.ContentValues
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.diplomatest.databinding.FragmentCalendarBinding
import java.util.Calendar
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*
import android.net.Uri
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat


class CalendarFragment : Fragment() {

    lateinit var binding: FragmentCalendarBinding

    private val events = mutableListOf<Event>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the CalendarView listener
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)

            val event = events.find { it.date == formattedDate }

            if (event != null) {
                // An event exists for this date, update the TextView with event details
                editOrDeleteEvent(event)
                binding.eventDetailsTextView.text = "Title: ${event.title}\nDescription: ${event.description}"
                //binding.eventDetailsTextView.visibility = View.VISIBLE
            } else {
                createEvent(formattedDate, "Event Title", "Event Description")

                // No event exists for this date, hide the TextView
                //binding.eventDetailsTextView.visibility = View.GONE
            }



            // Do something with the selected date
            // For example, you can display it in a Toast message
            //val selectedDateString = "Selected Date: $year/$month/$dayOfMonth"
            //Toast.makeText(requireContext(), selectedDateString, Toast.LENGTH_SHORT).show()
        }

    }

    private fun createEvent(date: String, title: String, description: String) {
        val newEvent = Event(events.size + 1, date, title, description)
        events.add(newEvent)

        binding.eventDetailsTextView.text = "Title: $title\nDescription: $description"

        // Example: Show a dialog for event creation
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_event, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Create Event")
            .setPositiveButton("Create") { dialog, which ->
                // Retrieve event details from dialog inputs (e.g., title, description)
                val title = dialogView.findViewById<EditText>(R.id.editTextTitle).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.editTextDescription).text.toString()

                // Update event details
                newEvent.title = title
                newEvent.description = description

                // Update UI to reflect the new event
                // (This step depends on your UI implementation)
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // User canceled event creation, remove the event from the list
                events.remove(newEvent)
            }
            .create()

        dialogBuilder.show()


    }

    private fun editOrDeleteEvent(event: Event) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_event, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Edit Event")
            .setPositiveButton("Save") { dialog, which ->
                // Retrieve edited event details from dialog inputs (e.g., title, description)
                val title = dialogView.findViewById<EditText>(R.id.editTextTitle).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.editTextDescription).text.toString()

                // Update event details
                event.title = title
                event.description = description

                // Update UI to reflect the edited event
                // (This step depends on your UI implementation)
            }
            .setNegativeButton("Delete") { dialog, which ->
                // User chose to delete the event, remove it from the list
                events.remove(event)

                // Update UI to reflect the deletion
                // (This step depends on your UI implementation)
            }
            .setNeutralButton("Cancel") { dialog, which ->
                // User canceled event editing, do nothing
            }
            .create()

        // Pre-fill dialog fields with event details
        dialogView.findViewById<EditText>(R.id.editTextTitle).setText(event.title)
        dialogView.findViewById<EditText>(R.id.editTextDescription).setText(event.description)

        dialogBuilder.show()
    }


}