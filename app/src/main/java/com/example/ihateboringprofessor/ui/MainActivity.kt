package com.example.ihateboringprofessor.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ihateboringprofessor.ui.theme.IHateBoringProfessorTheme
import com.example.ihateboringprofessor.viewmodel.MainViewModel
import com.example.ihateboringprofessor.viewmodel.CameraViewModel
import com.example.ihateboringprofessor.ui.screen.camera.CameraScreen
import com.example.ihateboringprofessor.ui.screen.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IHateBoringProfessorTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("main") {
                            MainScreen(mainViewModel, navController)
                        }
                        composable("next") {
                            val viewModel: CameraViewModel = hiltViewModel()
                            val state = viewModel.state.collectAsStateWithLifecycle().value
                            CameraScreen(
                                state = state,
                                onEvent = viewModel::onEvent,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IHateBoringProfessorTheme {
        val mainViewModel = MainViewModel()
        val navController = rememberNavController()
        MainScreen(mainViewModel, navController)
    }
}
