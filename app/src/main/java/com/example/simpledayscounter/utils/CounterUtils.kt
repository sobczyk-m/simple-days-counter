package com.example.simpledayscounter.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import com.example.simpledayscounter.R

class CounterUtils {

    fun convertIntToDP(intToConvert: Int): Float {
        return intToConvert * Resources.getSystem().displayMetrics.scaledDensity
    }

    fun createGradientDrawable(startColor: Int, centerColor: Int, endColor: Int)
            : GradientDrawable {
        val cornerRadiusInDP = convertIntToDP(28)
        val gradientDrawable = GradientDrawable()

        // Set the color array to draw gradient
        gradientDrawable.colors = intArrayOf(startColor, centerColor, endColor)
        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        gradientDrawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.cornerRadius = cornerRadiusInDP

        return gradientDrawable
    }

    fun getPastOrFutureCountingText(
        context: Context,
        differenceInDays: Int,
        timeUnit: String
    ): String {
        return if (differenceInDays < 0) {
            context.getString(R.string.app_widget_counting_text_time_ago, timeUnit)
        } else context.getString(R.string.app_widget_counting_text_time_left, timeUnit)
    }
}