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
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDate.now
import java.time.LocalDate.of
import java.time.Period.between
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

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

    private var selectedDateStorage: String = ""
    private var differenceInDaysStorage: Int = 0
    private var differenceInWeeksStorage: Double = 0.0
    private var differenceInMonthsStorage: Int = 0
    private var differenceInYearsStorage: Int = 0
    private var differenceInDaysOfMonthStorage: Int = 0

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

    private fun whichCountingMethodChecked() {
        when {
            rbDays?.isChecked == true -> {
                tvWdgCountingNumber?.text = differenceInDaysStorage.toString()
                llDayExclude?.visibility = View.VISIBLE
                tvWdgCountingText?.text = getString(R.string.app_widget_counting_text_days_left)
            }
            rbWeeks?.isChecked == true -> {
                tvWdgCountingNumber?.text = differenceInWeeksStorage.toString()
                llDayExclude?.visibility = View.GONE
                tvWdgCountingText?.text = getString(R.string.app_widget_counting_text_weeks_left)
            }
            rbMonths?.isChecked == true -> {
                llDayExclude?.visibility = View.GONE

                var monthFraction = 0
                val sumOfMonths = differenceInMonthsStorage + differenceInYearsStorage * 12

                fun fractionFromTenToNine() {
                    when (monthFraction) {
                        10 -> monthFraction = 9
                    }
                }

                fun calculateFraction(x: Double) {
                    val avgOfDaysInMonths = differenceInDaysStorage.toDouble() / sumOfMonths
                    val avgOfDayInMonth = (differenceInDaysOfMonthStorage / avgOfDaysInMonths)

                    monthFraction = ((x - avgOfDayInMonth) * 10).roundToInt().absoluteValue
                    fractionFromTenToNine()
                }

                when {
                    sumOfMonths > 1 -> {
                        if (differenceInDaysOfMonthStorage > 0) {
                            calculateFraction(1.0)
                        } else if (differenceInDaysOfMonthStorage < 0) {
                            calculateFraction(0.0)
                        }
                    }
                    sumOfMonths < -1 -> {
                        if (differenceInDaysOfMonthStorage > 0) {
                            calculateFraction(0.0)
                        } else if (differenceInDaysOfMonthStorage < 0) {
                            calculateFraction(-1.0)
                        }
                    }
                    sumOfMonths == 1 || sumOfMonths == -1 -> {
                        monthFraction =
                            ((((differenceInDaysStorage.toDouble() / 31) * 10).absoluteValue) - 10).roundToInt()
                        fractionFromTenToNine()
                    }
                    sumOfMonths == 0 -> {
                        monthFraction =
                            ((((differenceInDaysStorage.toDouble() / 31) * 10).absoluteValue).roundToInt())
                        fractionFromTenToNine()
                    }
                    differenceInDaysOfMonthStorage == 0 -> monthFraction = 0
                }

                tvWdgCountingNumber?.text = "${sumOfMonths.absoluteValue}.${monthFraction}"

                if (differenceInDaysStorage < 0) {
                    tvWdgCountingText?.text = getString(R.string.app_widget_counting_text_years_ago)
                } else tvWdgCountingText?.text =
                    getString(R.string.app_widget_counting_text_years_left)
            }
            rbYears?.isChecked == true -> {
                llDayExclude?.visibility = View.GONE

                val yearFraction =
                    ((differenceInMonthsStorage.toDouble() / 12) * 10).roundToInt().absoluteValue
                tvWdgCountingNumber?.text = "${differenceInYearsStorage}.${yearFraction}"

                if (differenceInDaysStorage < 0) {
                    tvWdgCountingText?.text = getString(R.string.app_widget_counting_text_years_ago)
                } else {
                    tvWdgCountingText?.text =
                        getString(R.string.app_widget_counting_text_years_left)
                }
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, mYear, mMonth, mDayOfMonth ->

            val selectedDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
            selectedDateStorage = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val sdfSelectedDate = sdf.parse(selectedDate)
            sdfSelectedDate?.let {
                val selectedDateInDays = sdfSelectedDate.time / (1000 * 60 * 60 * 24)
                val selectedDateInWeeks = selectedDateInDays.toDouble() / 7

                val sdfCurrentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                sdfCurrentDate?.let {
                    val currentDateInDays = sdfCurrentDate.time / (1000 * 60 * 60 * 24)
                    val currentDateInWeeks = currentDateInDays.toDouble() / 7

                    val differenceInDays = selectedDateInDays - currentDateInDays
                    val differenceInWeeks = selectedDateInWeeks - currentDateInWeeks
                    differenceInDaysStorage = differenceInDays.toInt()
                    differenceInWeeksStorage = differenceInWeeks
                }
            }

            val selectedLocaleDate = of(mYear, mMonth + 1, mDayOfMonth)
            val currentLocaleDate = now()
            val periodBetween = between(currentLocaleDate, selectedLocaleDate)

            val differenceInMonths = periodBetween.months
            val differenceInYears = periodBetween.years
            differenceInMonthsStorage = differenceInMonths
            differenceInYearsStorage = differenceInYears
            differenceInDaysOfMonthStorage = (currentLocaleDate.dayOfMonth - mDayOfMonth)

            etCountdownDate?.text = Editable.Factory.getInstance().newEditable(selectedDateStorage)
            whichCountingMethodChecked()
        },
            year,
            month,
            dayOfMonth
        )
        dpd.show()
    }
}