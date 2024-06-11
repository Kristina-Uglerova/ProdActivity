package com.example.prodactivity.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prodactivity.R
import com.example.prodactivity.viewModel.StatsViewModel
import com.example.prodactivity.ui.theme.ProdActivityTheme
import com.example.prodactivity.ui.theme.myDarkPink
import com.example.prodactivity.viewModel.AppViewModelProvider

@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val statsUiState by viewModel.statsUiState.collectAsState()
    val maxStreakGoal by viewModel.maxStreakGoal.collectAsState()
    val finishedGoalsCount by viewModel.finishedGoalsCount.collectAsState()
    val notFinishedGoalsCount by viewModel.notFinishedGoalsCount.collectAsState()
    val finishedGoalsPercentage by viewModel.finishedGoalsPercentage.collectAsState()
    Column(
        modifier = modifier) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(maxStreakGoal != null) {
                        Text(stringResource(R.string.top_streak_goal), Modifier.padding(bottom = 20.dp))
                        Text(text = maxStreakGoal!!.title, style = MaterialTheme.typography.titleLarge)
                    } else {
                        Text(stringResource(R.string.no_long_term_goals))
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(R.string.finished_goals), Modifier.padding(bottom = 20.dp))
                    Text(text = finishedGoalsCount.toString(), style = MaterialTheme.typography.titleLarge)
                }
            }
            Row(
                modifier = Modifier
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(R.string.goals_in_process), Modifier.padding(bottom = 20.dp))
                    Text(text = notFinishedGoalsCount.toString(), style = MaterialTheme.typography.titleLarge)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(R.string.finished_goals_percentage), Modifier.padding(bottom = 20.dp))
                    Text(text = String.format("%.2f%%", finishedGoalsPercentage), style = MaterialTheme.typography.titleLarge)
                }
            }
            if(!statsUiState.itemList.isEmpty()) {
                Row(modifier = Modifier) {
                    Text(stringResource(R.string.top_goals_streaks),  Modifier.padding(start = 20.dp), style = MaterialTheme.typography.titleLarge)
                }
                Column(modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))) {
                    statsUiState.itemList.forEach { item ->
                        val width = viewModel.calculateGoalWidth(item.streak)
                        Row(
                            modifier = Modifier
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(width)
                                    .padding(8.dp),
                                shape = RoundedCornerShape(16.dp),
                                CardDefaults.cardColors(myDarkPink)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                ) {

                                }
                            }

                        }
                        Text(
                            text = item.title,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }  
            }
        }
    }
}

@Preview
@Composable
fun StatsScreenPreview() {
    ProdActivityTheme {
        StatsScreen(
            modifier = Modifier.fillMaxHeight()
        )
    }
}
