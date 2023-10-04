package com.example.simpledayscounter

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpledayscounter.ui.HomeScreen
import com.example.simpledayscounter.ui.add_counter.CounterCreationScreen

enum class SimpleDaysCounterScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Creation(title = R.string.creation),
}

@Composable
fun SimpleDaysCounterApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SimpleDaysCounterScreen.Start.name,
        modifier = Modifier,
    ) {
        composable(route = SimpleDaysCounterScreen.Start.name) {
            HomeScreen(navController = navController)
        }
        composable(route = SimpleDaysCounterScreen.Creation.name) {
            CounterCreationScreen(navController = navController)
        }
    }
}
