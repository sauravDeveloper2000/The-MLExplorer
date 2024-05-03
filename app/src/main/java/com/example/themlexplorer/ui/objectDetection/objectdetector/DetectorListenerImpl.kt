package com.example.themlexplorer.ui.objectDetection.objectdetector

import com.example.themlexplorer.ui.objectDetection.model.AnalysisResult

class DetectorListenerImpl(
    val onResultsCallback: (result: AnalysisResult) -> Unit,
    val onErrorCallback: (error: String, errorCode: Int) -> Unit
) : DetectorListener {
    override fun onError(error: String, errorCode: Int) {
        onErrorCallback(error, errorCode)
    }

    override fun onResults(result: AnalysisResult) {
        onResultsCallback(result)
    }
}