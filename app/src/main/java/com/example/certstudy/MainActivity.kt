package com.example.certstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.certstudy.ui.screens.ExamDateSettingScreen
import com.example.certstudy.ui.screens.HomeScreen
import com.example.certstudy.ui.screens.QuizScreen
import com.example.certstudy.ui.theme.CertStudyTheme
import com.example.certstudy.viewmodel.StudyViewModel
import com.example.certstudy.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CertStudyTheme {
                CertStudyApp()
            }
        }
    }
}

@Composable
fun CertStudyApp() {
    val navController = rememberNavController()
    val studyViewModel: StudyViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val tabs = listOf(
        BottomTab.Home,
        BottomTab.Quiz
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(imageVector = tab.icon, contentDescription = tab.label)
                        },
                        label = null
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomTab.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomTab.Home.route) {
                HomeScreen(
                    studyViewModel = studyViewModel,
                    mainViewModel = mainViewModel,
                    onOpenExamDateSetting = { navController.navigate(Screen.ExamDateSetting.route) }
                )
            }
            composable(BottomTab.Quiz.route) {
                QuizScreen()
            }
            composable(Screen.ExamDateSetting.route) {
                ExamDateSettingScreen(
                    studyViewModel = studyViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

sealed class BottomTab(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    data object Home : BottomTab("home", "Home", Icons.Default.Home)
    data object Quiz : BottomTab("quiz", "Quiz", Icons.Default.Quiz)
}

sealed class Screen(val route: String) {
    data object ExamDateSetting : Screen("exam-date-setting")
}
