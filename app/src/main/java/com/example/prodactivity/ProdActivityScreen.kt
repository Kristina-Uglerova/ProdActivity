package com.example.prodactivity

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prodactivity.data.DataSource
import com.example.prodactivity.ui.TimersScreen
import com.example.prodactivity.ui.GoalsScreen
import com.example.prodactivity.ui.NoisesScreen
import com.example.prodactivity.ui.MainViewModel
import com.example.prodactivity.ui.StatsScreen

enum class ProdActivityScreen(@StringRes val title: Int) {
    NoisesScreen(title = R.string.menu_item_1),
    GoalsScreen(title = R.string.menu_item_2),
    StatsScreen(title = R.string.menu_item_3),
    TimersScreen(title = R.string.menu_item_4)
}

@Composable
fun ProdActivityApp (
    viewModel: MainViewModel = viewModel(),
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
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = ProdActivityScreen.NoisesScreen.name) {
                val context = LocalContext.current
                NoisesScreen(
                    options = DataSource.noises,
                    modifier = Modifier.fillMaxHeight(),
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
            composable(route = ProdActivityScreen.TimersScreen.name) {
                TimersScreen(
                    modifier = Modifier.fillMaxHeight()
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
                    .padding(dimensionResource(R.dimen.padding_medium)),
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
                    onClick = { navController.navigate(ProdActivityScreen.TimersScreen.name) }
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
