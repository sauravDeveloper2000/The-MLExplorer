package com.example.themlexplorer.ui.europeLandmarkRecognizer.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.themlexplorer.ui.europeLandmarkRecognizer.domain.Classification
import com.example.themlexplorer.ui.europeLandmarkRecognizer.domain.EuropeLandmarkClassifier

class LandmarkImageAnalyzer(
    private val classifier: EuropeLandmarkClassifier,
    private val onResult: (List<Classification>) -> Unit
) : ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image.toBitmap().createCrop(321, 321)

            val results = classifier.classify(image = bitmap, rotation = rotationDegrees)
            onResult(results)
        }
        frameSkipCounter++
        image.close()
    }

}