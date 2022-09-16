package com.example.simpledayscounter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.simpledayscounter.entities.CounterDao
import com.example.simpledayscounter.utils.CounterUtils
import com.example.simpledayscounter.utils.DateCalculationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {

    private var llCounterContainer: LinearLayout? = null

    private var referenceNumber = 1

    private var idsQuantity: Int? = null
    private lateinit var eventNameList: List<String>

    private lateinit var bgStartColorList: List<Int>
    private lateinit var bgCenterColorList: List<Int>
    private lateinit var bgEndColorList: List<Int>

    private lateinit var dayOfMonthList: List<Int>
    private lateinit var monthList: List<Int>
    private lateinit var yearList: List<Int>

    private lateinit var countingTypeList: List<CountingType>
    private lateinit var includeMondayList: List<Int>
    private lateinit var includeTuesdayList: List<Int>
    private lateinit var includeWednesdayList: List<Int>
    private lateinit var includeThursdayList: List<Int>
    private lateinit var includeFridayList: List<Int>
    private lateinit var includeSaturdayList: List<Int>
    private lateinit var includeSundayList: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llCounterContainer = findViewById(R.id.llCounterContainer)

        fun addCountdown() {
            val intent = Intent(this, AddCountdownActivity::class.java)
            startActivity(intent)
        }

        val fabAddCountdown: View = findViewById(R.id.fabAddCountdown)
        fabAddCountdown.setOnClickListener {
            addCountdown()
        }
        createCounterFromDatabase(this)
    }

    override fun onRestart() {
        super.onRestart()
        createCounterFromDatabase(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity_top_app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miSearch -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
            R.id.miSettings -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun inflateCounterView(context: Context) {
        val createCounter: View = LayoutInflater
            .from(context)
            .inflate(R.layout.app_widget, llCounterContainer, false)

        llCounterContainer?.addView(createCounter)

        val tvWdgCountingNumber = findViewById<TextView>(R.id.tvWdgCountingNumber)
        val tvWdgCountingText = findViewById<TextView>(R.id.tvWdgCountingText)
        val tvWdgEventName = findViewById<TextView>(R.id.tvWdgEventName)
        val llWdgContainer = findViewById<LinearLayout>(R.id.llWdgContainer)

        createCounter.id = referenceNumber
        tvWdgCountingNumber?.id = referenceNumber
        tvWdgCountingText?.id = referenceNumber
        tvWdgEventName?.id = referenceNumber
        llWdgContainer?.id = referenceNumber

        val countingType = countingTypeList[referenceNumber - 1]

        val year = yearList[referenceNumber - 1]
        val month = monthList[referenceNumber - 1]
        val dayOfMonth = dayOfMonthList[referenceNumber - 1]

        val sdfSelectedDate = DateCalculationUtils(year, month, dayOfMonth).sdfSelectedDate
        val sdfCurrentDate = DateCalculationUtils(year, month, dayOfMonth).sdfCurrentDate

        val daysOfWeekRemaining = DateCalculationUtils(year, month, dayOfMonth).countDaysOfWeek(
            sdfCurrentDate, sdfSelectedDate
        )

        val differenceInDays = DateCalculationUtils(year, month, dayOfMonth).differenceInDays()
        val differenceInYears = DateCalculationUtils(year, month, dayOfMonth).differenceInYears()
        val differenceInYearsFraction =
            DateCalculationUtils(year, month, dayOfMonth).differenceInYearsFraction()

        when (countingType) {
            CountingType.DAYS -> {
                val numberToDisplay =
                    (DateCalculationUtils(year, month, dayOfMonth)).excludeDayOfWeek(
                        differenceInDays,
                        daysOfWeekRemaining,
                        intToBoolean((includeMondayList[referenceNumber - 1])),
                        intToBoolean((includeTuesdayList[referenceNumber - 1])),
                        intToBoolean((includeWednesdayList[referenceNumber - 1])),
                        intToBoolean((includeThursdayList[referenceNumber - 1])),
                        intToBoolean((includeFridayList[referenceNumber - 1])),
                        intToBoolean((includeSaturdayList[referenceNumber - 1])),
                        intToBoolean((includeSundayList[referenceNumber - 1])),
                    ).toString()

                tvWdgCountingNumber.text = numberToDisplay
                CounterUtils().setCountingText(this, tvWdgCountingText, differenceInDays, "Days")
            }
            CountingType.WEEKS -> {
                val differenceInWeeks =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInWeeksInt()
                val differenceInWeeksFraction =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInWeeksFraction()
                val numberToDisplay = "$differenceInWeeks.$differenceInWeeksFraction"

                tvWdgCountingNumber.text = numberToDisplay
                CounterUtils().setCountingText(this, tvWdgCountingText, differenceInDays, "Weeks")

            }
            CountingType.MONTHS -> {
                val differenceInMonths =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInMonths()
                val differenceInMonthsFraction =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInMonthsFraction()

                val sumOfMonths = differenceInMonths + differenceInYears * 12
                val numberToDisplay =
                    "${sumOfMonths.absoluteValue}.${differenceInMonthsFraction}"

                tvWdgCountingNumber.text = numberToDisplay
                CounterUtils().setCountingText(this, tvWdgCountingText, differenceInDays, "Months")

            }
            CountingType.YEARS -> {
                val numberToDisplay =
                    "${differenceInYears.absoluteValue}.${differenceInYearsFraction}"

                tvWdgCountingNumber.text = numberToDisplay
                CounterUtils().setCountingText(this, tvWdgCountingText, differenceInDays, "Years")

            }
        }

        tvWdgEventName?.text = eventNameList[referenceNumber - 1]

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(
            CounterUtils().convertIntToDP(5).toInt(),
            0,
            CounterUtils().convertIntToDP(5).toInt(),
            CounterUtils().convertIntToDP(5).toInt()
        )
        createCounter.layoutParams = layoutParams

        createCounter.findViewById<View>(referenceNumber).setOnClickListener {
            Toast.makeText(context, "clicked = ${createCounter.id}", Toast.LENGTH_LONG)
                .show()
        }

        llWdgContainer?.background = CounterUtils().createGradientDrawable(
            bgStartColorList[referenceNumber - 1],
            bgCenterColorList[referenceNumber - 1],
            bgEndColorList[referenceNumber - 1]
        )
        ++referenceNumber
    }

    private fun createCounterFromDatabase(context: Context) {
        val dao: CounterDao = CounterDatabase.getInstance(this).counterDao

        lifecycleScope.launch {
            val getFromDatabase = launch(Dispatchers.IO) {
                idsQuantity = dao.getLastId()
                if (idsQuantity!! > 0) {
                    eventNameList = dao.getEventNameList()
                    bgStartColorList = dao.getBgStartColorList()
                    bgCenterColorList = dao.getBgCenterColorList()
                    bgEndColorList = dao.getBgEndColorList()
                    dayOfMonthList = dao.getDayOfMonthList()
                    monthList = dao.getMonthList()
                    yearList = dao.getYearList()
                    countingTypeList = dao.getCountingTypeList()
                    includeMondayList = dao.getIncludeMondayList()
                    includeTuesdayList = dao.getIncludeTuesdayList()
                    includeWednesdayList = dao.getIncludeWednesdayList()
                    includeThursdayList = dao.getIncludeThursdayList()
                    includeFridayList = dao.getIncludeFridayList()
                    includeSaturdayList = dao.getIncludeSaturdayList()
                    includeSundayList = dao.getIncludeSundayList()
                }
            }
            getFromDatabase.join()

            lifecycleScope.launch(Dispatchers.Main) {
                if (idsQuantity != null) {
                    while (referenceNumber <= idsQuantity!!) {
                        inflateCounterView(context)
                    }
                }
            }
        }
    }

    private fun intToBoolean(zeroOrOne: Int): Boolean {
        if (zeroOrOne == 1) return true
        if (zeroOrOne == 0) return false
        else throw Exception("Passed parameter must be 0 or 1")
    }
}