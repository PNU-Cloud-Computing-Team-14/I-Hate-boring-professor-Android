package com.example.ihateboringprofessor.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ihateboringprofessor.viewmodel.MainViewModel

@Composable
fun PINInputDialog(
    viewModel: MainViewModel,
    onConfirm: (String) -> Unit
) {
    val pin by viewModel.pin.collectAsState()

    AlertDialog(
        onDismissRequest = { viewModel.showDialog(false) },
        title = {
            Text(text = "PIN 번호를 입력하세요")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = pin,
                    onValueChange = { viewModel.setPin(it) },
                    label = { Text("PIN") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(pin)
                    viewModel.confirmPin()
                }
            ) {
                Text("네")
            }
        },
        dismissButton = {
            Button(
                onClick = { viewModel.showDialog(false) }
            ) {
                Text("아니오")
            }
        }
    )
}
