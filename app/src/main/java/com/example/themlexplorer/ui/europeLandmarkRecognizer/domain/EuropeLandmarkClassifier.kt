package com.example.themlexplorer.ui.europeLandmarkRecognizer.domain

import android.graphics.Bitmap

/**
 * EuropeLandmarkClassifier - It does the analysis of given image along with its rotation and give us list of output i.e., Classification.
 */
interface EuropeLandmarkClassifier {
    fun classify(
        image: Bitmap,
        rotation: Int
    ): List<Classification>
}