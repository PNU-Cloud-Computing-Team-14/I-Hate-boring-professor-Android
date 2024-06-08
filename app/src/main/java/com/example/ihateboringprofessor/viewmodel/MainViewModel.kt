package com.example.ihateboringprofessor.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    private val _pin = MutableStateFlow("")
    val pin: StateFlow<String> = _pin

    fun onScreenLoad() {
        // TODO: 메인스크린이 나왔을 때, 지금까지의 결과를 불러오는 API호출 후 결과 표시
        println("메인 화면 호출 : 결과 API 연동 할 것")
    }

    fun selectDate(date: LocalDate) {
        viewModelScope.launch {
            _selectedDate.emit(date)
        }
    }

    fun showDialog(show: Boolean) {
        viewModelScope.launch {
            _isDialogVisible.emit(show)
        }
    }

    fun setPin(pin: String) {
        viewModelScope.launch {
            _pin.emit(pin)
        }
    }

    fun confirmPin() {
        viewModelScope.launch {
            // PIN 확인 로직 추가

            _isDialogVisible.emit(false)
        }
    }
}
