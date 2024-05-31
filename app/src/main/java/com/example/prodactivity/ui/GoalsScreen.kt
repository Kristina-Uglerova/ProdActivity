package com.example.prodactivity.ui

import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prodactivity.R
import com.example.prodactivity.data.Goal
import com.example.prodactivity.ui.theme.ProdActivityTheme
import com.example.prodactivity.ui.theme.myLightGreen
import com.example.prodactivity.ui.theme.myDarkGreen
@Composable
fun GoalsScreen(
    modifier: Modifier = Modifier
) {
    var showAddGoalDialog by remember { mutableStateOf(false) }
    var showGoalInfoDialog by remember { mutableStateOf(false) }
    var goalName by remember { mutableStateOf("") }
    var goalDescription by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    val goals = remember { mutableStateListOf<Goal>() }
    var selectedGoal by remember { mutableStateOf<Goal?>(null) }

    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            goals.forEach { item ->
                Row(
                    modifier = Modifier
                        .clickable {
                            selectedGoal = item
                            showGoalInfoDialog = true
                        }
                ) {
                    Text(
                        item.goalName,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if(item.isLongTerm) {
                        Icon(
                            painter = painterResource(R.drawable.streaks_icon),
                            contentDescription = stringResource(R.string.streaks_icon)
                        )
                        Text(
                            item.streak.toString(),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.more_icon),
                            contentDescription = stringResource(R.string.more_icon)
                        )
                    }

                }
            }
            FloatingActionButton(
                onClick = { showAddGoalDialog = true },
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium)),
                containerColor = myLightGreen,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_icon),
                    contentDescription = stringResource(R.string.add_icon),
                    tint = myDarkGreen
                )
            }
        }

        if (showAddGoalDialog) {
            AlertDialog(
                onDismissRequest = { showAddGoalDialog = false },
                confirmButton = {
                    Button(onClick = {
                        showAddGoalDialog = false
                        goals.add(Goal(goalName, goalDescription, isChecked))
                    }) {
                        Text("OK")
                        goalName = ""
                        goalDescription = ""
                        isChecked = false
                    }
                },
                dismissButton = {
                    Button(onClick = { showAddGoalDialog = false }) {
                        Text("Cancel")
                        goalName = ""
                        goalDescription = ""
                        isChecked = false
                    }
                },
                text = {
                    Column {
                        TextField(
                            value = goalName,
                            singleLine = true,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .fillMaxWidth(),
                            onValueChange = {
                                if(it.length <= 14) {
                                    goalName = it
                                }
                            },
                            label = { Text("Enter your goal") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )
                        )
                        TextField(
                            value = goalDescription,
                            singleLine = false,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .heightIn(100.dp)
                                .fillMaxWidth(),
                            onValueChange = { goalDescription = it },
                            label = { Text("Enter goal description") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { isChecked = it }
                            )
                            Text(
                                stringResource(R.string.longterm_checkbox),
                            )
                        }

                    }
                }
            )
        }
        if(showGoalInfoDialog) {
            Dialog(
                onDismissRequest = { showGoalInfoDialog = false },
                content = {
                    Card(
                        modifier = Modifier
                            .height(200.dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                selectedGoal?.goalName ?: "",
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Text(
                                selectedGoal?.goalDescription ?: "",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            if(selectedGoal!!.isLongTerm) {
                                Row {
                                    Icon(
                                        painter = painterResource(R.drawable.streaks_icon),
                                        contentDescription = stringResource(R.string.streaks_icon)
                                    )
                                    Text(selectedGoal!!.streak.toString())
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun GoalsScreenPreview() {
    ProdActivityTheme {
        GoalsScreen(
            modifier = Modifier.fillMaxHeight()
        )
    }
}