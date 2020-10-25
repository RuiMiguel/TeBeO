package com.ruiskas.tebeo.ui.base.extensions

import android.content.res.Resources
import android.util.TypedValue

object MetricUtils {
    fun dpToPx(resources: Resources, dip: Int): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip.toFloat(),
            resources.displayMetrics
        ).toInt()
}