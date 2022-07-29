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

    private var rbDays: RadioButton? = null
    private var rbWeeks: RadioButton? = null
    private var rbMonths: RadioButton? = null
    private var rbYears: RadioButton? = null

    private var llDayExclude: LinearLayout? = null
    private var etCountdownTitle: EditText? = null
    private var etCountdownDate: EditText? = null

    private var tvWdgEventName: TextView? = null
    private var tvWdgCountingText: TextView? = null
    private var tvWdgCountingNumber: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_countdown)

        rbDays = findViewById(R.id.rbDays)
        rbWeeks = findViewById(R.id.rbWeeks)
        rbMonths = findViewById(R.id.rbMonths)
        rbYears = findViewById(R.id.rbYears)

        llDayExclude = findViewById(R.id.llDayExclude)
        etCountdownTitle = findViewById(R.id.etCountdownTitle)
        etCountdownDate = findViewById(R.id.etCountdownDate)

        tvWdgEventName = findViewById(R.id.tvWdgEventName)
        tvWdgCountingText = findViewById(R.id.tvWdgCountingText)
        tvWdgCountingNumber = findViewById(R.id.tvWdgCountingNumber)

        fun ifRbDaysChecked() {
            if (rbDays?.isChecked == true) {
                llDayExclude?.visibility = View.VISIBLE
            } else {
                llDayExclude?.visibility = View.GONE
            }
        }

        rbDays?.setOnClickListener {
            ifRbDaysChecked()
        }
        rbWeeks?.setOnClickListener {
            ifRbDaysChecked()
        }
        rbMonths?.setOnClickListener {
            ifRbDaysChecked()
        }
        rbYears?.setOnClickListener {
            ifRbDaysChecked()
        }

        etCountdownTitle?.doAfterTextChanged {
            tvWdgEventName?.text = etCountdownTitle?.text
        }

        etCountdownDate?.setOnClickListener {
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