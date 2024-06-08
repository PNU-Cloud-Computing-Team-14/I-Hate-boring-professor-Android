package com.example.ihateboringprofessor.ui.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ihateboringprofessor.ui.component.HorizontalCalendar
import com.example.ihateboringprofessor.ui.component.TitleLabel
import com.example.ihateboringprofessor.ui.component.PINInputDialog
import com.example.ihateboringprofessor.viewmodel.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TitleLabel(name = "지난 강의 평가")
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalCalendar(viewModel = mainViewModel)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { mainViewModel.showDialog(true) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text("수업 참여하기")
            }

            val isDialogVisible by mainViewModel.isDialogVisible.collectAsState()
            if (isDialogVisible) {
                PINInputDialog(
                    viewModel = mainViewModel,
                    onConfirm = {
                        if (it == "1234") {
                            navController.navigate("next")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
