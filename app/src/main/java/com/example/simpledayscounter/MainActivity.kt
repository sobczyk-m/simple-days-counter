package com.example.simpledayscounter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpledayscounter.entities.CounterDao
import com.example.simpledayscounter.utils.CounterUtils
import com.example.simpledayscounter.utils.DateCalculationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {

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

    private var rvCounter: RecyclerView? = null
    private var counterDisplayList = mutableListOf<CounterDisplay>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCounter = findViewById(R.id.rvCounter)

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

        val eventNameToDisplay: String = eventNameList[referenceNumber - 1]
        lateinit var countingNumberToDisplay: String
        lateinit var countingTextToDisplay: String

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
                countingNumberToDisplay =
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

                countingTextToDisplay =
                    CounterUtils().getPastOrFutureCountingText(this, differenceInDays, "Days")
            }
            CountingType.WEEKS -> {
                val differenceInWeeks =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInWeeksInt()
                val differenceInWeeksFraction =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInWeeksFraction()

                countingNumberToDisplay = "$differenceInWeeks.$differenceInWeeksFraction"
                countingTextToDisplay =
                    CounterUtils().getPastOrFutureCountingText(this, differenceInDays, "Weeks")
            }
            CountingType.MONTHS -> {
                val differenceInMonths =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInMonths()
                val differenceInMonthsFraction =
                    DateCalculationUtils(year, month, dayOfMonth).differenceInMonthsFraction()

                val sumOfMonths = differenceInMonths + differenceInYears * 12

                countingNumberToDisplay =
                    "${sumOfMonths.absoluteValue}.${differenceInMonthsFraction}"
                countingTextToDisplay =
                    CounterUtils().getPastOrFutureCountingText(this, differenceInDays, "Months")
            }
            CountingType.YEARS -> {

                countingNumberToDisplay =
                    "${differenceInYears.absoluteValue}.${differenceInYearsFraction}"
                countingTextToDisplay =
                    CounterUtils().getPastOrFutureCountingText(this, differenceInDays, "Years")
            }
        }

        val backgroundToDisplay: GradientDrawable = CounterUtils().createGradientDrawable(
            bgStartColorList[referenceNumber - 1],
            bgCenterColorList[referenceNumber - 1],
            bgEndColorList[referenceNumber - 1]
        )

        val counterToDisplay = (CounterDisplay(
            eventNameToDisplay, countingNumberToDisplay, countingTextToDisplay,
            backgroundToDisplay
        ))

        val adapter = CounterDisplayAdapter(counterDisplayList)
        rvCounter?.adapter = adapter
        rvCounter?.layoutManager = LinearLayoutManager(context)
        counterDisplayList.add(counterToDisplay)
        adapter.notifyItemInserted(counterDisplayList.size - 1)

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