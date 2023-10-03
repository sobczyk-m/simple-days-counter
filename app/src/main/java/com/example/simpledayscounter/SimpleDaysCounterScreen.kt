package com.example.simpledayscounter

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.simpledayscounter.ui.HomeScreen
import com.example.simpledayscounter.ui.add_counter.ColorPicker
import com.example.simpledayscounter.ui.add_counter.CounterCreationScreen

enum class SimpleDaysCounterScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Creation(title = R.string.creation),
    ColorPicker(title = R.string.creation),
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDaysCounterAppBar(
    currentScreen: SimpleDaysCounterScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.btn_back)
                    )
                }
            }
        },
        actions = {
            if (currentScreen.name == SimpleDaysCounterScreen.Creation.name) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = stringResource(R.string.btn_save)
                    )
                }
            }
        }
    )
}

@Composable
fun SimpleDaysCounterApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = SimpleDaysCounterScreen.valueOf(
        backStackEntry?.destination?.route ?: SimpleDaysCounterScreen.Start.name
    )

    Scaffold(
        topBar = {
            SimpleDaysCounterAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            if (currentScreen == SimpleDaysCounterScreen.Start) {
                FloatingActionButton(onClick =
                {
                    navController.navigate(SimpleDaysCounterScreen.Creation.name)
                }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.fab_add_counter)
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SimpleDaysCounterScreen.Start.name,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = SimpleDaysCounterScreen.Start.name) {
                HomeScreen()
            }
            composable(route = SimpleDaysCounterScreen.Creation.name) {
                CounterCreationScreen()
            }
            composable(route = SimpleDaysCounterScreen.ColorPicker.name) {
                ColorPicker(onColorChange = {/* TODO */ })
            }
        }
    }
}
