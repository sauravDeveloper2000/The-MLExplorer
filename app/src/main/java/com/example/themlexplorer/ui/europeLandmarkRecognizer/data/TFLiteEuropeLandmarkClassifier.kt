package com.example.themlexplorer.ui.europeLandmarkRecognizer.data

import android.content.Context
import android.graphics.Bitmap
import android.view.Surface
import com.example.themlexplorer.ui.europeLandmarkRecognizer.domain.Classification
import com.example.themlexplorer.ui.europeLandmarkRecognizer.domain.EuropeLandmarkClassifier
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

/**
 * This class is the one which has implemented the EuropeLandmarkClassifier and provides the implementation.
 */
class TFLiteEuropeLandmarkClassifier(
    private val context: Context,
    private val threshold: Float = 0.5f,
    private val maxResults: Int = 1
) : EuropeLandmarkClassifier {

    private var classifier: ImageClassifier? = null

    private fun setUpClassifier() {
        val baseOptions = BaseOptions.builder()
            .setNumThreads(2)
            .build()

        val options = ImageClassifier.ImageClassifierOptions.builder()
            .setBaseOptions(baseOptions)
            .setMaxResults(maxResults)
            .setScoreThreshold(threshold)
            .build()

        try {
            classifier = ImageClassifier.createFromFileAndOptions(
                context,
                "landmark_recognizer_model.tflite",
                options
            )
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    override fun classify(image: Bitmap, rotation: Int): List<Classification> {
        if (classifier == null) {
            setUpClassifier()
        }

        val imageProcessor = ImageProcessor.Builder().build()
        val tensorImage = imageProcessor.process(
            TensorImage.fromBitmap(image)
        )
        val imageProcessingOptions =
            ImageProcessingOptions.builder().setOrientation(getOrientationFromRotation(rotation))
                .build()
        val results = classifier?.classify(tensorImage, imageProcessingOptions)

        return results?.flatMap { classifications ->
            classifications.categories.map { category ->
                Classification(
                    name = category.displayName,
                    score = category.score
                )
            }
        }?.distinctBy { it.name } ?: emptyList()
    }

    /**
     * An private helper function to get orientation from rotation.
     */
    private fun getOrientationFromRotation(
        rotation: Int
    ): ImageProcessingOptions.Orientation {
        return when (rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }
    }
}