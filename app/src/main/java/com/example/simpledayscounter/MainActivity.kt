package com.example.simpledayscounter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun addCountdown() {
            val intent = Intent(this, AddCountdownActivity::class.java)
            startActivity(intent)
        }

        val fabAddCountdown: View = findViewById(R.id.fabAddCountdown)
        fabAddCountdown.setOnClickListener {
            addCountdown()
        }

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
}




