package com.example.prodactivity.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodactivity.viewModel.NoiseViewModel
import com.example.prodactivity.R
import com.example.prodactivity.viewModel.MainViewModel
import com.example.prodactivity.viewModel.TimerViewModel
import com.example.prodactivity.ui.theme.myLightPink

enum class ProdActivityScreen(@StringRes val title: Int) {
    NoisesScreen(title = R.string.menu_item_1),
    GoalsScreen(title = R.string.menu_item_2),
    StatsScreen(title = R.string.menu_item_3),
    TimerScreen(title = R.string.menu_item_4)
}

@Composable
fun ProdActivityApp (
    viewModel: MainViewModel = viewModel(),
    noiseViewModel: NoiseViewModel = viewModel(),
    timerViewModel: TimerViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold (
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = ProdActivityScreen.NoisesScreen.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = ProdActivityScreen.NoisesScreen.name) {
                NoisesScreen(
                    options = noiseViewModel.noises,
                    onItemClick = {
                        noiseViewModel.updateNoiseItem(it.copy(isPlaying = !it.isPlaying))
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = ProdActivityScreen.GoalsScreen.name) {
                GoalsScreen(
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = ProdActivityScreen.StatsScreen.name) {
                StatsScreen(
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = ProdActivityScreen.TimerScreen.name) {
                TimerScreen(
                    modifier = Modifier.fillMaxHeight(),
                    timerViewModel
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .background(myLightPink),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.Bottom
            ) {
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(ProdActivityScreen.NoisesScreen.name) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.noises_icon),
                        contentDescription = stringResource(R.string.menu_item_1)
                    )
                }
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(ProdActivityScreen.GoalsScreen.name) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.goals_icon),
                        contentDescription = stringResource(R.string.menu_item_2)
                    )
                }
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(ProdActivityScreen.StatsScreen.name) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.stats_icon),
                        contentDescription = stringResource(R.string.menu_item_3)
                    )
                }
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(ProdActivityScreen.TimerScreen.name) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.timers_icon),
                        contentDescription = stringResource(R.string.menu_item_4)
                    )
                }
            }
        }
    }
}
