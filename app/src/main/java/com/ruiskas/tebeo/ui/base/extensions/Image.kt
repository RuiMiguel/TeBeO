package com.ruiskas.tebeo.ui.base.extensions

import android.widget.ImageView

fun ImageView.blackAndWhite() {
    val matrix = android.graphics.ColorMatrix()
    matrix.setSaturation(0f)

    val filter = android.graphics.ColorMatrixColorFilter(matrix)
    this.colorFilter = filter
}