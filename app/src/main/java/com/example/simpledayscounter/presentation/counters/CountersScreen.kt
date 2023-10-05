package com.example.simpledayscounter.presentation.counters

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simpledayscounter.R
import com.example.simpledayscounter.SimpleDaysCounterScreen
import com.example.simpledayscounter.presentation.counters.components.CountersList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountersScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CountersViewModel = viewModel(factory = CountersViewModel.Factory)
) {
    val countersUiState by viewModel.countersUiState.collectAsState()

    Scaffold(modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    Text(stringResource(id = R.string.app_name))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick =
            { navController.navigate(route = SimpleDaysCounterScreen.AddCounter.name) }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.fab_add_counter)
                )
            }
        }
    ) { innerPadding ->
        CountersList(
            counterList = countersUiState.counterList,
            modifier = modifier.padding(innerPadding),
            onCounterSwipe = { counterId -> viewModel.deleteCounter(counterId) }
        )
    }
}