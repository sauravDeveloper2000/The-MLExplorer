package com.example.themlexplorer.ui.objectDetection.model

import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult

/**
 * This data class AnalysisResult holds the result of detection done by object detector on the input frame
 */
data class AnalysisResult(
    val results: List<ObjectDetectorResult>,
    val inferenceTime: Long,
    val inputImageWidth: Int,
    val inputImageHeight: Int
)
