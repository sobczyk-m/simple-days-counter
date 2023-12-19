package com.example.simpledayscounter

import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpledayscounter.presentation.add_counter.AddCounterScreen
import com.example.simpledayscounter.presentation.counters.CountersScreen


enum class SimpleDaysCounterScreen(@StringRes val title: Int) {
    Counters(title = R.string.app_name),
    AddCounter(title = R.string.add_counter),
}

@Composable
fun SimpleDaysCounterApp(
    navController: NavHostController = rememberNavController()
) {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels

    NavHost(
        navController = navController,
        startDestination = SimpleDaysCounterScreen.Counters.name,
        modifier = Modifier,
    ) {
        composable(route = SimpleDaysCounterScreen.Counters.name,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { screenWidth },
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -screenWidth },
                    animationSpec = tween(500)
                )
            }
        ) {
            CountersScreen(navController = navController)
        }
        composable(route = SimpleDaysCounterScreen.AddCounter.name,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { screenWidth },
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -screenWidth },
                    animationSpec = tween(500)
                )
            }
        ) {
            AddCounterScreen(navController = navController)
        }
    }
}
