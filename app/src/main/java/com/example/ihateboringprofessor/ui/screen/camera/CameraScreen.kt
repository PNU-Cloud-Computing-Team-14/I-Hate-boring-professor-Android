package com.example.ihateboringprofessor.ui.screen.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ihateboringprofessor.ui.component.CameraComponent
import com.example.ihateboringprofessor.ui.component.CheckPermissionComponent

@Composable
fun CameraScreen(
    state: CameraState,
    onEvent: (CameraEvent) -> Unit
) {
    CheckPermissionComponent { permit ->
        onEvent(CameraEvent.GrantCameraPermission(permit))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.DarkGray),
            Alignment.Center
        ) {
            Text(
                text = "감정 분석 중",
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            if (state.permitCamera) {
                CameraComponent(
                    modifier = Modifier
                        .fillMaxSize(),
                    selfMode = state.selfCam,
                    takeAction = state.capture,
                    receiveImageUrl = { bitmap ->
                        if (bitmap != null)
                            onEvent(CameraEvent.GetImageBitmap(bitmap))
                        onEvent(CameraEvent.CaptureProcessed)
                    }
                )
            } else {
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxSize()
                )
            }
        }

        if (state.showLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
