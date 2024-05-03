package com.example.themlexplorer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.themlexplorer.navigation.AppNavigation
import com.example.themlexplorer.navigation.Destinations
import com.example.themlexplorer.navigation.StartDestinationViewModel
import com.example.themlexplorer.ui.europeLandmarkRecognizer.data.TFLiteEuropeLandmarkClassifier
import com.example.themlexplorer.ui.europeLandmarkRecognizer.domain.Classification
import com.example.themlexplorer.ui.europeLandmarkRecognizer.presentation.LandmarkImageAnalyzer
import com.example.themlexplorer.ui.theme.TheMLExplorerTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private val startDestinationViewModel: StartDestinationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasCameraPermission()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), 0
            )
        }
        firebaseAuth = Firebase.auth
        setContent {
            TheMLExplorerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var classifications by remember {
                        mutableStateOf(emptyList<Classification>())
                    }
                    var analyzer = remember {
                        LandmarkImageAnalyzer(
                            classifier = TFLiteEuropeLandmarkClassifier(applicationContext),
                            onResult = {
                                classifications = it
                            }
                        )
                    }
                    var controller = remember {
                        LifecycleCameraController(applicationContext).apply {
                            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                            setImageAnalysisAnalyzer(
                                ContextCompat.getMainExecutor(applicationContext),
                                analyzer
                            )
                        }
                    }

                    val startDestination by startDestinationViewModel._startingDestination.collectAsState()

                    var threshold by rememberSaveable {
                        mutableFloatStateOf(0.5f)
                    }
                    var maxResults by rememberSaveable {
                        mutableIntStateOf(3)
                    }

                    AppNavigation(
                        startingDestination = startDestination,
                        controller = controller,
                        classification = classifications,
                        threshold = threshold,
                        maxResults = maxResults
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener {
            val currentUser = it.currentUser
            if (currentUser != null){
                startDestinationViewModel._startingDestination.value = Destinations.HomeScreen
            } else{
                startDestinationViewModel._startingDestination.value = Destinations.PreAuth
            }
        }
    }


    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}
