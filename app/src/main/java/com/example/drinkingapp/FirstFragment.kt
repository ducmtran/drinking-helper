package com.example.drinkingapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val act = activity
        val alarmBuilder: AlarmInitializer
        if (act != null) {
            alarmBuilder = AlarmInitializer(act)
            initializeToggle(view, alarmBuilder)
            initializeTimeDisplay(view)
        }
    }

    private fun getReminderState(): Boolean {
        val pref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return false
        return pref.getBoolean(getString(R.string.reminder_state), false)
    }

    private fun initializeToggle(view: View, alarmBuilder: AlarmInitializer) {
        val toggle = view.findViewById<Switch>(R.id.switch1)
        toggle.isChecked = getReminderState()

        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmBuilder.setAlarm();
            } else {
                alarmBuilder.disableAlarm();
            }
        }
    }

    private fun initializeTimeDisplay(view: View) {
        val disp = view.findViewById<EditText>(R.id.end_time_display)
        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
        val time = pref?.getString(getString(R.string.end_time), "09:00")

        disp.setOnClickListener {  }

        disp.text = Editable.Factory.getInstance().newEditable(time)
    }


}