package com.example.themlexplorer.ui.objectDetection.objectdetector

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.SystemClock
import android.util.Log
import androidx.camera.core.ImageProxy
import com.example.themlexplorer.ui.objectDetection.model.AnalysisResult
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult

class ObjectDetectorHelper(
    var threshold: Float = THRESHOLD,
    var maxResults: Int = MAX_RESULTS,
    var runningMode: RunningMode = RunningMode.LIVE_STREAM,
    val context: Context,
    var objectDetectorListener: DetectorListener? = null
) {
    companion object {
        const val THRESHOLD = 0.5F
        const val MAX_RESULTS = 5
        const val OTHER_ERROR = 0
        const val TAG = "ObjectDetectorHelper"
    }

    private var objectDetector: com.google.mediapipe.tasks.vision.objectdetector.ObjectDetector? =
        null

    init {
        setupObjectDetector()
    }

    private fun setupObjectDetector() {
        val baseOptions = BaseOptions.builder()
        baseOptions.setDelegate(Delegate.CPU)

        val modelName = "efficientdet_lite0.tflite"

        baseOptions.setModelAssetPath(modelName)

        when (runningMode) {
            RunningMode.IMAGE -> TODO()
            RunningMode.VIDEO -> TODO()
            RunningMode.LIVE_STREAM -> {
                if (objectDetectorListener == null) {
                    throw IllegalStateException(
                        "ObjectDetectorListener must be set when running mode is LIVE_STREAM."
                    )
                }
            }
        }

        try {
            val optionBuilder =
                com.google.mediapipe.tasks.vision.objectdetector.ObjectDetector.ObjectDetectorOptions.builder()
                    .setBaseOptions(baseOptions.build())
                    .setScoreThreshold(threshold)
                    .setMaxResults(maxResults)
                    .setRunningMode(runningMode)

            when (runningMode) {
                RunningMode.IMAGE -> TODO()
                RunningMode.VIDEO -> TODO()
                RunningMode.LIVE_STREAM -> {
                    optionBuilder.setRunningMode(runningMode)
                        .setResultListener(this::returnLivestreamResult)
                        .setErrorListener(this::returnLivestreamError)
                }
            }

            val options = optionBuilder.build()
            objectDetector =
                com.google.mediapipe.tasks.vision.objectdetector.ObjectDetector.createFromOptions(
                    context,
                    options
                )
        } catch (e: IllegalStateException) {
            objectDetectorListener?.onError(
                "Object detector failed to initialize. See error logs for details"
            )
            Log.e(TAG, "TFLite failed to load model with error:" + e.message)
        } catch (e: RuntimeException) {
            objectDetectorListener?.onError(
                "Object detector failed to initialize. See error logs for details"
            )
            Log.e(
                TAG, "Object detector failed to load model with error: " + e.message
            )
        }
    }

    fun detectLivestreamFrame(
        image: ImageProxy
    ) {
        if (runningMode != RunningMode.LIVE_STREAM) {
            throw IllegalArgumentException(
                "Attempting to call detectLivestreamFrame" +
                        "while not using RunningMode.LIVE_STREAM"
            )
        }

        val frameTime = SystemClock.uptimeMillis()
        val bitmapBuffer = Bitmap.createBitmap(
            image.width,
            image.height,
            Bitmap.Config.ARGB_8888
        )

        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
        image.close()

        val matrix =
            Matrix().apply { postRotate(image.imageInfo.rotationDegrees.toFloat()) }

        val rotatedBitmap =
            Bitmap.createBitmap(
                bitmapBuffer,
                0,
                0,
                bitmapBuffer.width,
                bitmapBuffer.height,
                matrix,
                true
            )

        val mpImage = BitmapImageBuilder(rotatedBitmap).build()
        detectAsync(mpImage, frameTime)
    }

    fun detectAsync(
        mpImage: MPImage, frameTime: Long
    ) {
        objectDetector?.detectAsync(mpImage, frameTime)
    }

    private fun returnLivestreamResult(
        result: ObjectDetectorResult,
        inputFrame: MPImage
    ) {
        val finishTimeMs = SystemClock.uptimeMillis()
        val inferenceTime = finishTimeMs - result.timestampMs()

        objectDetectorListener?.onResults(
            AnalysisResult(
                listOf(result),
                inferenceTime = inferenceTime,
                inputImageWidth = inputFrame.width,
                inputImageHeight = inputFrame.height
            )
        )
    }

    private fun returnLivestreamError(
        error: RuntimeException
    ) {
        objectDetectorListener?.onError(
            error.message ?: "An unknown error occurred"
        )
    }
}