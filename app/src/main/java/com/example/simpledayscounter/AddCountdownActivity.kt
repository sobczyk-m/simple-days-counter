package com.example.simpledayscounter

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDate.now
import java.time.LocalDate.of
import java.time.Period.between

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

    private var selectedDateStorage: String? = ""
    private var differenceInDaysStorage: Int? = 0
    private var differenceInWeeksStorage: Int? = 0
    private var differenceInMonthsStorage: Int? = 0
    private var differenceInYearsStorage: Int? = 0

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

        fun whichCountingMethodChecked() {
            if (rbDays?.isChecked == true) {
                llDayExclude?.visibility = View.VISIBLE
            } else {
                llDayExclude?.visibility = View.GONE
            }
        }

        rbDays?.setOnClickListener {
            whichCountingMethodChecked()
        }
        rbWeeks?.setOnClickListener {
            whichCountingMethodChecked()
        }
        rbMonths?.setOnClickListener {
            whichCountingMethodChecked()
        }
        rbYears?.setOnClickListener {
            whichCountingMethodChecked()
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

        val dpd = DatePickerDialog(this, { _, mYear, mMonth, mDayOfMonth ->

            val selectedDate = "$mDayOfMonth/${mMonth + 1}/$mYear"

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

            val sdfSelectedDate = sdf.parse(selectedDate)
            val selectedDateInDays = sdfSelectedDate.time / (1000 * 60 * 60 * 24)
            val selectedDateInWeeks = sdfSelectedDate.time / (1000 * 60 * 60 * 24 * 7)

            val sdfCurrentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
            val currentDateInDays = sdfCurrentDate.time / (1000 * 60 * 60 * 24)
            val currentDateInWeeks = sdfCurrentDate.time / (1000 * 60 * 60 * 24 * 7)

            val differenceInDays = selectedDateInDays - currentDateInDays
            val differenceInWeeks = selectedDateInWeeks - currentDateInWeeks

            val selectedLocaleDate = of(mYear, mMonth + 1, mDayOfMonth)
            val currentLocaleDate = now()

            val periodBetween = between(selectedLocaleDate, currentLocaleDate)
            val differenceInMonths = periodBetween.months
            val differenceInYears = periodBetween.years

            selectedDateStorage = selectedDate
            differenceInDaysStorage = differenceInDays.toInt()
            differenceInWeeksStorage = differenceInWeeks.toInt()
            differenceInMonthsStorage = differenceInMonths
            differenceInYearsStorage = differenceInYears

        },
            year,
            month,
            dayOfMonth
        )
        dpd.show()
    }
}