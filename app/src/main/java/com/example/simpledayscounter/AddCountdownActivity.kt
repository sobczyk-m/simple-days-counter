package com.example.simpledayscounter

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.simpledayscounter.entities.Counter
import com.example.simpledayscounter.entities.CounterDao
import com.example.simpledayscounter.utils.CounterUtils
import com.example.simpledayscounter.utils.DateCalculationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vadiole.colorpicker.ColorModel
import vadiole.colorpicker.ColorPickerDialog
import java.util.*
import kotlin.math.absoluteValue

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

    private var chbMonday: CheckBox? = null
    private var chbTuesday: CheckBox? = null
    private var chbWednesday: CheckBox? = null
    private var chbThursday: CheckBox? = null
    private var chbFriday: CheckBox? = null
    private var chbSaturday: CheckBox? = null
    private var chbSunday: CheckBox? = null

    private var selectedDayOfMonth: Int = 1
    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0
    private var selectedDate: String = ""

    private var differenceInDays: Int = 0
    private var differenceInDaysOfMonth: Int = 0
    private var differenceInWeeks: Int = 0
    private var differenceInWeeksFraction: Int = 0
    private var differenceInMonths: Int = 0
    private var differenceInMonthsFraction: Int = 0
    private var differenceInYears: Int = 0
    private var differenceInYearsFraction: Int = 0

    private var ibColorFirst: ImageButton? = null
    private var ibColorSecond: ImageButton? = null
    private var ibColorThird: ImageButton? = null
    private var llWdgContainer: LinearLayout? = null

    private var wdgStartColor: Int = -3052635
    private var wdgCenterColor: Int = -7952153
    private var wdgEndColor: Int = -10486799

    private var countingType: CountingType = CountingType.DAYS
    private var daysOfWeekRemaining: List<Int> = listOf(0)
    private var includeMonday: Boolean = true
    private var includeTuesday: Boolean = true
    private var includeWednesday: Boolean = true
    private var includeThursday: Boolean = true
    private var includeFriday: Boolean = true
    private var includeSaturday: Boolean = true
    private var includeSunday: Boolean = true

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

        chbMonday = findViewById(R.id.chbMonday)
        chbTuesday = findViewById(R.id.chbTuesday)
        chbWednesday = findViewById(R.id.chbWednesday)
        chbThursday = findViewById(R.id.chbThursday)
        chbFriday = findViewById(R.id.chbFriday)
        chbSaturday = findViewById(R.id.chbSaturday)
        chbSunday = findViewById(R.id.chbSunday)

        ibColorFirst = findViewById(R.id.ibColorFirst)
        ibColorSecond = findViewById(R.id.ibColorSecond)
        ibColorThird = findViewById(R.id.ibColorThird)
        llWdgContainer = findViewById(R.id.llWdgContainer)

        rbDays?.setOnClickListener {
            whichCountingMethodChecked()
            countingType = CountingType.DAYS
        }
        rbWeeks?.setOnClickListener {
            whichCountingMethodChecked()
            countingType = CountingType.WEEKS
        }
        rbMonths?.setOnClickListener {
            whichCountingMethodChecked()
            countingType = CountingType.MONTHS
        }
        rbYears?.setOnClickListener {
            whichCountingMethodChecked()
            countingType = CountingType.YEARS
        }

        etCountdownTitle?.doAfterTextChanged {
            tvWdgEventName?.text = etCountdownTitle?.text
        }

        etCountdownDate?.setOnClickListener {
            showDatePicker()
        }

        ibColorFirst?.setOnClickListener { showColorPicker(wdgStartColor) }
        ibColorSecond?.setOnClickListener { showColorPicker(wdgCenterColor) }
        ibColorThird?.setOnClickListener { showColorPicker(wdgEndColor) }
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
            android.R.id.home -> {
                AlertDialog.Builder(this)
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")
                    .setPositiveButton(android.R.string.ok) { _, _ -> this.finish() }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
            }
            R.id.miSave -> {
                if (selectedDate == "") {
                    AlertDialog.Builder(this)
                        .setTitle("Save entry")
                        .setMessage("Choose the date of your event to proceed")
                        .setPositiveButton(android.R.string.ok, null)
                        .show()
                    val colorRed = Color.RED
                    etCountdownDate?.setHintTextColor(colorRed)
                } else {
                    val dao: CounterDao = CounterDatabase.getInstance(this).counterDao

                    val counterToSave = Counter(
                        null,
                        tvWdgEventName?.text.toString(),
                        wdgStartColor,
                        wdgCenterColor,
                        wdgEndColor,
                        selectedDayOfMonth,
                        selectedMonth,
                        selectedYear,
                        countingType,
                        includeMonday,
                        includeTuesday,
                        includeWednesday,
                        includeThursday,
                        includeFriday,
                        includeSaturday,
                        includeSunday
                    )

                    lifecycleScope.launch(Dispatchers.IO) {
                        dao.insertCounter(counterToSave)
                    }
                    this.finish()
                }
            }
        }
        return true
    }

    private fun whichCountingMethodChecked() {
        when {
            rbDays?.isChecked == true -> {
                llDayExclude?.visibility = View.VISIBLE

                chbMonday?.setOnCheckedChangeListener { _, _ ->
                    includeMonday = !includeMonday

                    CounterUtils().setCountingNumber(
                        tvWdgCountingNumber, (DateCalculationUtils(
                            selectedYear, selectedMonth, selectedDayOfMonth
                        ).excludeDayOfWeek(
                            differenceInDays,
                            daysOfWeekRemaining,
                            includeMonday,
                            includeTuesday,
                            includeWednesday,
                            includeThursday,
                            includeFriday,
                            includeSaturday,
                            includeSunday
                        )).toString()
                    )
                }
                chbTuesday?.setOnCheckedChangeListener { _, _ ->
                    includeTuesday = !includeTuesday

                    CounterUtils().setCountingNumber(
                        tvWdgCountingNumber, (DateCalculationUtils(
                            selectedYear, selectedMonth, selectedDayOfMonth
                        ).excludeDayOfWeek(
                            differenceInDays,
                            daysOfWeekRemaining,
                            includeMonday,
                            includeTuesday,
                            includeWednesday,
                            includeThursday,
                            includeFriday,
                            includeSaturday,
                            includeSunday
                        )).toString()
                    )
                }
                chbWednesday?.setOnCheckedChangeListener { _, _ ->
                    includeWednesday = !includeWednesday

                    CounterUtils().setCountingNumber(
                        tvWdgCountingNumber, (DateCalculationUtils(
                            selectedYear, selectedMonth, selectedDayOfMonth
                        ).excludeDayOfWeek(
                            differenceInDays,
                            daysOfWeekRemaining,
                            includeMonday,
                            includeTuesday,
                            includeWednesday,
                            includeThursday,
                            includeFriday,
                            includeSaturday,
                            includeSunday
                        )).toString()
                    )
                }
                chbThursday?.setOnCheckedChangeListener { _, _ ->
                    includeThursday = !includeThursday

                    CounterUtils().setCountingNumber(
                        tvWdgCountingNumber, (DateCalculationUtils(
                            selectedYear, selectedMonth, selectedDayOfMonth
                        ).excludeDayOfWeek(
                            differenceInDays,
                            daysOfWeekRemaining,
                            includeMonday,
                            includeTuesday,
                            includeWednesday,
                            includeThursday,
                            includeFriday,
                            includeSaturday,
                            includeSunday
                        )).toString()
                    )
                }
                chbFriday?.setOnCheckedChangeListener { _, _ ->
                    includeFriday = !includeFriday

                    CounterUtils().setCountingNumber(
                        tvWdgCountingNumber, (DateCalculationUtils(
                            selectedYear, selectedMonth, selectedDayOfMonth
                        ).excludeDayOfWeek(
                            differenceInDays,
                            daysOfWeekRemaining,
                            includeMonday,
                            includeTuesday,
                            includeWednesday,
                            includeThursday,
                            includeFriday,
                            includeSaturday,
                            includeSunday
                        )).toString()
                    )
                }
                chbSaturday?.setOnCheckedChangeListener { _, _ ->
                    includeSaturday = !includeSaturday

                    CounterUtils().setCountingNumber(
                        tvWdgCountingNumber, (DateCalculationUtils(
                            selectedYear, selectedMonth, selectedDayOfMonth
                        ).excludeDayOfWeek(
                            differenceInDays,
                            daysOfWeekRemaining,
                            includeMonday,
                            includeTuesday,
                            includeWednesday,
                            includeThursday,
                            includeFriday,
                            includeSaturday,
                            includeSunday
                        )).toString()
                    )
                }
                chbSunday?.setOnCheckedChangeListener { _, _ ->
                    includeSunday = !includeSunday

                    CounterUtils().setCountingNumber(
                        tvWdgCountingNumber, (DateCalculationUtils(
                            selectedYear, selectedMonth, selectedDayOfMonth
                        ).excludeDayOfWeek(
                            differenceInDays,
                            daysOfWeekRemaining,
                            includeMonday,
                            includeTuesday,
                            includeWednesday,
                            includeThursday,
                            includeFriday,
                            includeSaturday,
                            includeSunday
                        )).toString()
                    )
                }

                val numberToDisplay =
                    (DateCalculationUtils(
                        selectedYear, selectedMonth, selectedDayOfMonth
                    ).excludeDayOfWeek(
                        differenceInDays,
                        daysOfWeekRemaining,
                        includeMonday,
                        includeTuesday,
                        includeWednesday,
                        includeThursday,
                        includeFriday,
                        includeSaturday,
                        includeSunday
                    )).toString()

                CounterUtils().setCountingNumber(tvWdgCountingNumber, numberToDisplay)
                CounterUtils().setCountingText(this, tvWdgCountingText, differenceInDays, "Days")
            }
            rbWeeks?.isChecked == true -> {
                llDayExclude?.visibility = View.GONE

                val numberToDisplay = "$differenceInWeeks.$differenceInWeeksFraction"

                CounterUtils().setCountingNumber(tvWdgCountingNumber, numberToDisplay)
                CounterUtils().setCountingText(this, tvWdgCountingText, differenceInDays, "Weeks")
            }
            rbMonths?.isChecked == true -> {
                llDayExclude?.visibility = View.GONE

                val sumOfMonths = differenceInMonths + differenceInYears * 12
                val numberToDisplay =
                    "${sumOfMonths.absoluteValue}.${differenceInMonthsFraction}"

                CounterUtils().setCountingNumber(tvWdgCountingNumber, numberToDisplay)
                CounterUtils().setCountingText(this, tvWdgCountingText, differenceInDays, "Months")
            }
            rbYears?.isChecked == true -> {
                llDayExclude?.visibility = View.GONE

                val numberToDisplay =
                    "${differenceInYears.absoluteValue}.${differenceInYearsFraction}"

                CounterUtils().setCountingNumber(tvWdgCountingNumber, numberToDisplay)
                CounterUtils().setCountingText(this, tvWdgCountingText, differenceInDays, "Years")
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this, { _, mYear, mMonth, mDayOfMonth ->

                selectedDate = "$mDayOfMonth/${mMonth + 1}/$mYear"

                selectedDayOfMonth = mDayOfMonth
                selectedMonth = mMonth
                selectedYear = mYear

                differenceInDays =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).differenceInDays()
                differenceInDaysOfMonth =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).differenceInDaysOfMonth()

                differenceInWeeks =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).differenceInWeeksInt()
                differenceInWeeksFraction =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).differenceInWeeksFraction()

                differenceInMonths =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).differenceInMonths()
                differenceInMonthsFraction =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).differenceInMonthsFraction()

                differenceInYears =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).differenceInYears()
                differenceInYearsFraction =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).differenceInYearsFraction()

                val sdfSelectedDate =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).sdfSelectedDate
                val sdfCurrentDate =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).sdfCurrentDate

                daysOfWeekRemaining =
                    DateCalculationUtils(mYear, mMonth, mDayOfMonth).countDaysOfWeek(
                        sdfCurrentDate,
                        sdfSelectedDate
                    )

                etCountdownDate?.text =
                    Editable.Factory.getInstance().newEditable(selectedDate)
                whichCountingMethodChecked()
            },
            year,
            month,
            dayOfMonth
        )
        dpd.show()
    }

    private fun setWdgBackground(backgroundImage: GradientDrawable) {
        llWdgContainer?.background = backgroundImage
    }

    private fun showColorPicker(initialColor: Int) {
        val cpd: ColorPickerDialog = ColorPickerDialog.Builder()

            //  set initial (default) color
            .setInitialColor(initialColor)
            //  set Color Model. ARGB, RGB or HSV
            .setColorModel(ColorModel.ARGB)
            //  set is user be able to switch color model
            .setColorModelSwitchEnabled(true)
            //  set your localized string resource for OK button
            .setButtonOkText(android.R.string.ok)
            //  set your localized string resource for Cancel button
            .setButtonCancelText(android.R.string.cancel)
            //  callback for picked color (required)
            .onColorSelected { color: Int ->
                // check which color button pushed and set its new color
                when (initialColor) {
                    wdgStartColor -> {
                        wdgStartColor = color
                        ibColorFirst?.setBackgroundColor(color)
                    }
                    wdgCenterColor -> {
                        wdgCenterColor = color
                        ibColorSecond?.setBackgroundColor(color)
                    }
                    wdgEndColor -> {
                        wdgEndColor = color
                        ibColorThird?.setBackgroundColor(color)
                    }
                }
                // set new colors for GradientDrawable
                setWdgBackground(
                    CounterUtils().createGradientDrawable(
                        wdgStartColor,
                        wdgCenterColor,
                        wdgEndColor
                    )
                )
            }
            //  create dialog
            .create()

        // show dialog from Activity
        cpd.show(supportFragmentManager, "color_picker")
    }
}