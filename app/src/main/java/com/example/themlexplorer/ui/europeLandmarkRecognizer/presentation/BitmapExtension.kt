package com.example.themlexplorer.ui.europeLandmarkRecognizer.presentation

import android.graphics.Bitmap

fun Bitmap.createCrop(
    desiredWidth: Int,
    desiredHeight: Int
): Bitmap {
    val xStart = (width - desiredWidth) / 2
    val yStart = (height - desiredHeight) / 2

    if (xStart < 0 || yStart < 0 || desiredWidth > width || desiredHeight > height) {
        throw IllegalArgumentException("invalid arguments for center cropping.")
    }

    return Bitmap.createBitmap(this, xStart, yStart, desiredWidth, desiredHeight)
}