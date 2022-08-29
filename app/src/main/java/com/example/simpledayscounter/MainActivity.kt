package com.example.simpledayscounter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var llCounterContainer: LinearLayout? = null

    private var numberToIncrease = 1
    private var idsQuantity: Int? = null
    private var eventNameList: List<String>? = null
    private var bgStartColor: List<Int>? = null
    private var bgCenterColor: List<Int>? = null
    private var bgEndColor: List<Int>? = null
    private var countingTextList: List<String>? = null
    private var countingNumberList: List<String>? = null
    private var dao: CounterDao? = null

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

        dao = CounterDatabase.getInstance(this).counterDao
        createCounterFromDatabase(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity_top_app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miSearch -> Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
            R.id.miSettings -> Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun convertIntToDP(numberToConvert: Int): Int {
        return (numberToConvert * resources.displayMetrics.scaledDensity).toInt()
    }

    private fun createGradientDrawable(startColor: Int, centerColor: Int, endColor: Int)
            : GradientDrawable {
        // Scale Float to DP
        val cornerRadiusToDP = 28 * resources.displayMetrics.scaledDensity

        val gradientDrawable = GradientDrawable()
        // Set the color array to draw gradient
        gradientDrawable.colors = intArrayOf(
            startColor,
            centerColor,
            endColor,
        )
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        gradientDrawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.cornerRadius = cornerRadiusToDP

        return gradientDrawable
    }

    private fun inflateCounterView(context: Context) {
        val createCounter: View = LayoutInflater
            .from(context)
            .inflate(R.layout.app_widget, llCounterContainer, false)

        val tvWdgCountingNumber = findViewById<TextView>(R.id.tvWdgCountingNumber)
        val tvWdgCountingText = findViewById<TextView>(R.id.tvWdgCountingText)
        val tvWdgEventName = findViewById<TextView>(R.id.tvWdgEventName)
        val llWdgContainer = findViewById<LinearLayout>(R.id.llWdgContainer)

        llCounterContainer?.addView(createCounter)

        createCounter.id = numberToIncrease
        tvWdgCountingNumber?.id = numberToIncrease
        tvWdgCountingText?.id = numberToIncrease
        tvWdgEventName?.id = numberToIncrease
        llWdgContainer?.id = numberToIncrease

        tvWdgCountingNumber?.text = countingNumberList?.get(numberToIncrease - 1)
        tvWdgCountingNumber?.text
        tvWdgCountingText?.text = countingTextList?.get(numberToIncrease - 1)
        tvWdgEventName?.text = eventNameList?.get(numberToIncrease - 1).toString()

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(convertIntToDP(5), 0, convertIntToDP(5), convertIntToDP(5))
        createCounter.layoutParams = layoutParams

        // Set Listener for every inflatedView
        createCounter.findViewById<View>(numberToIncrease).setOnClickListener {
            Toast.makeText(context, "clicked = ${createCounter.id}", Toast.LENGTH_LONG)
                .show()
        }

        llWdgContainer?.background = createGradientDrawable(
            bgStartColor!![numberToIncrease - 1],
            bgCenterColor!![numberToIncrease - 1],
            bgEndColor!![numberToIncrease - 1]
        )
        ++numberToIncrease
    }

    private fun createCounterFromDatabase(context: Context) {

        lifecycleScope.launch {

            val getFromDatabase = launch(Dispatchers.IO) {
                idsQuantity = dao?.getLastId()
                eventNameList = dao?.getEventNameList()
                bgStartColor = dao?.getBgStartColorList()
                bgCenterColor = dao?.getBgCenterColorList()
                bgEndColor = dao?.getBgEndColorList()
                countingTextList = dao?.getCountingTextList()
                countingNumberList = dao?.getCountingNumberList()
            }

            getFromDatabase.join()
            lifecycleScope.launch(Dispatchers.Main) {
                if (idsQuantity != null) {
                    while (numberToIncrease <= idsQuantity!!) {
                        inflateCounterView(context)
                    }
                }
            }
        }
    }
}