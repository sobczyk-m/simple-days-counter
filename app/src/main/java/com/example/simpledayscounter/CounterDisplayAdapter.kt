package com.example.simpledayscounter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpledayscounter.utils.CounterUtils

class CounterDisplayAdapter(
    private var counters: List<CounterDisplay>
) : RecyclerView.Adapter<CounterDisplayAdapter.CounterDisplayViewHolder>() {

    inner class CounterDisplayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterDisplayViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.app_widget, parent, false)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(
            0,
            0,
            0,
            CounterUtils().convertIntToDP(5).toInt()
        )
        view.layoutParams = layoutParams

        return CounterDisplayViewHolder(view)
    }

    override fun onBindViewHolder(holder: CounterDisplayViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.tvWdgEventName).text = counters[position].eventName
            findViewById<TextView>(R.id.tvWdgCountingText).text = counters[position].countingText
            findViewById<TextView>(R.id.tvWdgCountingNumber).text =
                counters[position].countingNumber
            findViewById<LinearLayout>(R.id.llWdgContainer).background =
                counters[position].background
        }
    }

    override fun getItemCount(): Int {
        return counters.size
    }
}