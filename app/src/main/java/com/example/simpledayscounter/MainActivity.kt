package com.example.simpledayscounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabAddCountdown: View = findViewById(R.id.fabAddCountdown)
        fabAddCountdown.setOnClickListener { view ->
//            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
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




