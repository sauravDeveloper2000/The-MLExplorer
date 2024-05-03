package com.example.themlexplorer.ui.objectDetection.objectdetector

import com.example.themlexplorer.ui.objectDetection.model.AnalysisResult
import com.example.themlexplorer.ui.objectDetection.objectdetector.ObjectDetectorHelper.Companion.OTHER_ERROR

interface DetectorListener {
    fun onError(error: String, errorCode: Int = OTHER_ERROR)
    fun onResults(result: AnalysisResult)
}