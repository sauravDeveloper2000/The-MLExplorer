package com.example.themlexplorer.ui.objectDetection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.themlexplorer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectDetectionScreen(
    modifier: Modifier,
    threshold: Float,
    maxResults: Int
) {
    /**
     * This state hold the calculation time taken by an detector to detect an object into an input frame.
     */
    var inferenceTime by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) {
        Column(
            modifier = modifier.padding(it)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                CameraView(
                    threshold = threshold,
                    maxResults = maxResults,
                    setInferenceTime = { time ->
                        inferenceTime = time
                    }
                )
            }

            Box(modifier = Modifier.padding(10.dp)) {
                Text(text = "Inference Time: $inferenceTime ms")
            }
        }
    }
}