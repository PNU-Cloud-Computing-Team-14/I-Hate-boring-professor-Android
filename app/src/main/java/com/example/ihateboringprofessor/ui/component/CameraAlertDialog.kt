package com.example.ihateboringprofessor.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CameraAlertDialog(
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* 다이얼로그가 취소될 때 실행할 내용 */ },
        title = { Text("수업이 종료되었습니다.") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                }
            ) {
                Text("확인")
            }
        },
        dismissButton = {}
    )
}
