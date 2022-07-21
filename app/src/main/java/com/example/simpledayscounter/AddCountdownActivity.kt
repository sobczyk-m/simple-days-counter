package com.example.simpledayscounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton

class AddCountdownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_countdown)

        val rbDays = findViewById<RadioButton>(R.id.rbDays)
        val rbWeeks = findViewById<RadioButton>(R.id.rbWeeks)
        val rbMonths = findViewById<RadioButton>(R.id.rbMonths)
        val rbYears = findViewById<RadioButton>(R.id.rbYears)

        val llDayExclude = findViewById<LinearLayout>(R.id.llDayExclude)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

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

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> this.finish()
        }
        return true
    }
}