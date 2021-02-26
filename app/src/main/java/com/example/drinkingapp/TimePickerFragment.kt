package com.example.drinkingapp

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.format.DateFormat.is24HourFormat
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException
import android.text.format.DateFormat
import android.widget.TimePicker
import java.util.*

class TimePickerFragment(val fragmentContext: Context) : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        TODO("Not yet implemented")
    }


}