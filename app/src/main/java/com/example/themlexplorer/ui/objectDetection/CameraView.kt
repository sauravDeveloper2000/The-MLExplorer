package com.example.themlexplorer.ui.objectDetection

import android.Manifest
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.themlexplorer.ui.objectDetection.objectdetector.DetectorListenerImpl
import com.example.themlexplorer.ui.objectDetection.objectdetector.ObjectDetectorHelper
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraView(
    threshold: Float,
    maxResults: Int,
    setInferenceTime: (Int) -> Unit
) {
    val permissionState: PermissionState =
        rememberPermissionState(permission = Manifest.permission.CAMERA)

    /**
     *  Now using LaunchedEffect side effect we check that do we have camera permission.
     *  And if we do not have then request for camera permission to user.
     */
    LaunchedEffect(key1 = Unit) {
        if (!permissionState.hasPermission) {
            permissionState.launchPermissionRequest()
        }
    }

    if (!permissionState.hasPermission) {
        Text(text = "No camera permission is allowed.")
        return
    }

    /**
     *  At this point we have our permission to use the camera. Now we define some states
     *  So first state is used to hold object detector detection result.
     */
    var results by remember {
        mutableStateOf<ObjectDetectorResult?>(null)
    }

    /**
     * The 2 states to hold the dimension of the camera frame or camera preview
     */
    var frameWidth by remember {
        mutableStateOf(3)
    }

    var frameHeight by remember {
        mutableStateOf(4)
    }

    var active by remember {
        mutableStateOf(true)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            active = false
            cameraProviderFuture.get().unbindAll()
        }
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val cameraPreviewSize = getFittedBoxSize(
            containerSize = Size(
                width = this.maxWidth.value,
                height = this.maxHeight.value
            ),
            boxSize = Size(
                width = frameWidth.toFloat(),
                height = frameHeight.toFloat()
            )
        )

        Box(
            modifier = Modifier
                .width(cameraPreviewSize.width.dp)
                .height(cameraPreviewSize.height.dp)
        ) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val executor = ContextCompat.getMainExecutor(ctx)
                    cameraProviderFuture.addListener(
                        {

                            val cameraProvider = cameraProviderFuture.get()
                            val preview = Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            val cameraSelector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()

                            val imageAnalyzer = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                                .build()

                            val backgroundExecutor = Executors.newSingleThreadExecutor()
                            backgroundExecutor.execute {
                                val objectDetector = ObjectDetectorHelper(
                                    threshold = threshold,
                                    maxResults = maxResults,
                                    context = context,
                                    objectDetectorListener = DetectorListenerImpl(
                                        onErrorCallback = { _, _ -> Unit },
                                        onResultsCallback = {
                                            frameWidth = it.inputImageWidth
                                            frameHeight = it.inputImageHeight

                                            if (active) {
                                                results = it.results.first()
                                                setInferenceTime(it.inferenceTime.toInt())
                                            }
                                        }
                                    ),
                                    runningMode = RunningMode.LIVE_STREAM
                                )
                                imageAnalyzer.setAnalyzer(
                                    backgroundExecutor,
                                    objectDetector::detectLivestreamFrame
                                )
                            }

                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                imageAnalyzer,
                                preview
                            )
                        },
                        executor
                    )
                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )
            results?.let {
                ResultsOverlay(result = it, frameWidth = frameWidth, frameHeight = frameHeight)
            }
        }
    }
}