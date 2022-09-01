package com.example.simpledayscounter.utils

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class CounterUtils {

    val differenceInMonths = 123

    fun convertIntToDP(intToConvert: Int): Float {
        return intToConvert * Resources.getSystem().displayMetrics.scaledDensity
    }

    fun createGradientDrawable(startColor: Int, centerColor: Int, endColor: Int)
            : GradientDrawable {
        val cornerRadiusInDP = convertIntToDP(28)
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
        gradientDrawable.cornerRadius = cornerRadiusInDP

        return gradientDrawable
    }
//




}