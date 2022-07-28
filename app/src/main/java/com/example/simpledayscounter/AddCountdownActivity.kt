package com.example.simpledayscounter

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import java.util.*

class AddCountdownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_countdown)

        val rbDays = findViewById<RadioButton>(R.id.rbDays)
        val rbWeeks = findViewById<RadioButton>(R.id.rbWeeks)
        val rbMonths = findViewById<RadioButton>(R.id.rbMonths)
        val rbYears = findViewById<RadioButton>(R.id.rbYears)

        val llDayExclude = findViewById<LinearLayout>(R.id.llDayExclude)
        val etCountdownTitle = findViewById<EditText>(R.id.etCountdownTitle)
        val tvWdgEventName = findViewById<TextView>(R.id.tvWdgEventName)
        val etCountdownDate = findViewById<EditText>(R.id.etCountdownDate)

        fun ifRbDaysChecked() {
            if (rbDays.isChecked) {
                llDayExclude.visibility = View.VISIBLE
            } else {
                llDayExclude.visibility = View.GONE
            }
        }

        rbDays.setOnClickListener {
            ifRbDaysChecked()
        }
        rbWeeks.setOnClickListener {
            ifRbDaysChecked()
        }
        rbMonths.setOnClickListener {
            ifRbDaysChecked()
        }
        rbYears.setOnClickListener {
            ifRbDaysChecked()
        }

        etCountdownTitle.doAfterTextChanged {
            tvWdgEventName.text = etCountdownTitle.text
        }

        etCountdownDate.setOnClickListener {
            showDatePicker()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_countdown_activity_top_app_bar_menu, menu)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Create Countdown"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> this.finish()
        }
        return true
    }

    private fun showDatePicker() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val etCountdownDate = findViewById<EditText>(R.id.etCountdownDate)

        val dpd = DatePickerDialog(
            this,
            { _, mYear, mMonth, mDayOfMonth ->

                val selectedDate = "$mDayOfMonth/${mMonth + 1}/$mYear"

                etCountdownDate.text = Editable.Factory.getInstance().newEditable(selectedDate)

            },
            year,
            month,
            dayOfMonth
        ).show()
    }
}