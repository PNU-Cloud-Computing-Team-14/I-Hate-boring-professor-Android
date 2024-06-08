package com.example.ihateboringprofessor.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ihateboringprofessor.ui.screen.camera.CameraEvent
import com.example.ihateboringprofessor.ui.screen.camera.CameraState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    init {
        startAutoCapture()
    }

    fun onEvent(event: CameraEvent) {
        when (event) {
            CameraEvent.SelfCamMode -> selfCamMode()
            CameraEvent.Capture -> captureImage()
            CameraEvent.CaptureProcessed -> captureProcessed()
            is CameraEvent.GetImageBitmap -> getImageBitmap(event.bitmap)
            is CameraEvent.GrantCameraPermission -> grantCameraPermission(event.permission)
        }
    }

    private fun startAutoCapture() {
        viewModelScope.launch {
            while (true) {
                delay(10000) // 10초 대기
                captureImage()
            }
        }
    }

    private fun selfCamMode() {
        _state.update { it.copy(selfCam = !_state.value.selfCam) }
    }

    private fun captureImage() {
        _state.update { it.copy(capture = true, showLoading = true) }
    }

    private fun captureProcessed() {
        _state.update { it.copy(capture = false, showLoading = false) }
    }

    private fun getImageBitmap(bitmap: Bitmap) {
        _state.update { it.copy(imageBitmap = bitmap) }
        // TODO: 서버로 이미지 전송 로직 추가
        println("전송")
    }

    private fun grantCameraPermission(permission: Boolean) {
        _state.update { it.copy(permitCamera = permission) }
    }
}
