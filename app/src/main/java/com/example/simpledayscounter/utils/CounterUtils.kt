package com.example.simpledayscounter.utils

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable

class CounterUtils {

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
}