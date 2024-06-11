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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prodactivity.R
import com.example.prodactivity.data.Goal
import com.example.prodactivity.ui.theme.ProdActivityTheme
import com.example.prodactivity.ui.theme.myLightGreen
import com.example.prodactivity.ui.theme.myDarkGreen
import com.example.prodactivity.viewModel.AppViewModelProvider
import com.example.prodactivity.viewModel.GoalsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun GoalsScreen(
    modifier: Modifier = Modifier,
    viewModel: GoalsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    var showGoalEditDialog by rememberSaveable { mutableStateOf(false) }
    var showGoalInfoDialog by rememberSaveable { mutableStateOf(false) }
    val parameters by viewModel.parameters.collectAsState()
    var selectedGoal by remember { mutableStateOf<Goal?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val goalsUiState by viewModel.goalsUiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .weight(1f)
            .padding(dimensionResource(R.dimen.padding_medium))) {
            goalsUiState.itemList.forEach { item ->
                if(!item.isFinished) {
                    var expanded by rememberSaveable { mutableStateOf(false) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                selectedGoal = item
                                showGoalInfoDialog = true
                            }

                    ) {
                        Text(
                            item.title,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        if(item.isLongterm) {
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
                            onClick = { expanded = true  }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.more_icon),
                                contentDescription = stringResource(R.string.more_icon)
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(text =  {Text(stringResource(R.string.edit_button )) }, onClick = {
                                viewModel.editParameters(GoalEditDialogParameters(
                                    item.title,
                                    item.description,
                                    item.isLongterm,
                                    true,
                                    confirm = {
                                            title, description, isLongterm ->
                                        coroutineScope.launch {
                                            item.title = title
                                            item.description = description
                                            item.isLongterm = isLongterm
                                            viewModel.updateGoal(item)
                                        }
                                    }))
                                showGoalEditDialog = true
                                expanded = false
                            })
                            DropdownMenuItem(text = {  Text(stringResource(R.string.delete_button )) }, onClick = {
                                coroutineScope.launch {
                                    viewModel.deleteGoal(item)
                                }
                                expanded = false
                            })
                            if(item.isLongterm) {
                                DropdownMenuItem(text = { Text(stringResource(R.string.streak_button)) },
                                    onClick = {
                                        item.streak++
                                        coroutineScope.launch {
                                            viewModel.updateGoal(item)
                                        }
                                        expanded = false
                                    })
                            }
                            DropdownMenuItem(text = { Text(stringResource( R.string.finish_goal_button )) }, onClick = {
                                coroutineScope.launch {
                                    item.isFinished = true
                                    viewModel.updateGoal(item)
                                }
                                expanded = false
                            })
                        }

                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                viewModel.editParameters(GoalEditDialogParameters(
                    "",
                    "",
                    false,
                    false,
                    confirm = {
                            title, description, isLongterm ->
                        coroutineScope.launch {
                            viewModel.saveGoal(title, description, isLongterm)
                        }
                    }))
                showGoalEditDialog = true
            },
            modifier = Modifier
                .align(Alignment.End)
                .offset(y = (-100).dp, x = (-10).dp),
            containerColor = myLightGreen,
            shape = CircleShape
        ) {
            Icon(
                painter = painterResource(R.drawable.add_icon),
                contentDescription = stringResource(R.string.add_icon),
                tint = myDarkGreen
            )
        }

        if(showGoalEditDialog) {
            GoalEditDialog(parameters = parameters, onCloseDialog =  {
                showGoalEditDialog = false
            }, viewModel = viewModel, coroutineScope = coroutineScope)
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
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                selectedGoal?.title ?: "",
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Text(
                                selectedGoal?.description ?: "",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            if(selectedGoal!!.isLongterm) {
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

@Composable
fun GoalEditDialog(
    parameters: GoalEditDialogParameters,
    onCloseDialog: () -> Unit,
    viewModel: GoalsViewModel,
    coroutineScope: CoroutineScope
) {
    var newTitle by remember { mutableStateOf(parameters.title) }
    var newDescription by remember { mutableStateOf(parameters.description) }
    var newIsLongterm by remember { mutableStateOf(parameters.isLongterm) }
    val originalTitle = parameters.title
    var isGoalValid by remember { mutableStateOf(parameters.defaultEnabled) }
    AlertDialog(
        onDismissRequest = { onCloseDialog() },
        confirmButton = {
            Button(
                enabled = isGoalValid,
                onClick = {
                    parameters.confirm(newTitle, newDescription, newIsLongterm)
                    onCloseDialog()

                }
            ) {
                R.string.ok_button
            }
        },
        dismissButton = {
            Button(onClick = { onCloseDialog() }) {
                R.string.cancel_button
            }
        },
        text = {
            Column {
                TextField(
                    value = newTitle,
                    singleLine = true,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    onValueChange = {
                        coroutineScope.launch {
                           isGoalValid = viewModel.isGoalValid(newTitle, originalTitle)
                        }
                        if(it.length <= 14) {
                            newTitle = it
                            viewModel.editParameters(parameters.copy(title = it))
                        }
                    },
                    label = { R.string.enter_goal_name },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
                TextField(
                    value = newDescription,
                    singleLine = false,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .heightIn(100.dp)
                        .fillMaxWidth(),
                    onValueChange = { newDescription = it },
                    label = { R.string.enter_goal_description },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = newIsLongterm,
                        onCheckedChange = { newIsLongterm = it }
                    )
                    Text(
                        stringResource(R.string.longterm_checkbox),
                    )
                }
            }
        }
    )
}

data class GoalEditDialogParameters(
    var title: String = "",
    var description: String = "",
    var isLongterm: Boolean = false,
    var defaultEnabled: Boolean = false,
    var confirm: (String, String, Boolean) -> Unit = { _, _, _ -> },
)