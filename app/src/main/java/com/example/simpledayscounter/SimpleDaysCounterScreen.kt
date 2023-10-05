package com.example.simpledayscounter

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpledayscounter.presentation.counters.CountersScreen
import com.example.simpledayscounter.presentation.add_counter.AddCounterScreen

enum class SimpleDaysCounterScreen(@StringRes val title: Int) {
    Counters(title = R.string.app_name),
    AddCounter(title = R.string.add_counter),
}

@Composable
fun SimpleDaysCounterApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SimpleDaysCounterScreen.Counters.name,
        modifier = Modifier,
    ) {
        composable(route = SimpleDaysCounterScreen.Counters.name) {
            CountersScreen(navController = navController)
        }
        composable(route = SimpleDaysCounterScreen.AddCounter.name) {
            AddCounterScreen(navController = navController)
        }
    }
}
