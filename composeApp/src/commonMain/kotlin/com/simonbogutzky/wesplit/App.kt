package com.simonbogutzky.wesplit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

// ====================================
// Constants
// ====================================
object NavigationRoutes {
    const val HOME = "home"
    const val SELECT_PEOPLE = "selectPeople"
}

object AppConstants {
    val TIP_PERCENTAGES = listOf(10, 15, 20, 25, 0)
    val PEOPLE_RANGE = 2..99
    const val DEFAULT_NUMBER_OF_PEOPLE = 2
    const val DEFAULT_TIP_PERCENTAGE = 20
}

@Composable
@Preview
fun App() {
    // ====================================
    // Properties
    // ====================================
    val navController = rememberNavController()
    var numberOfPeople by remember { mutableStateOf(AppConstants.DEFAULT_NUMBER_OF_PEOPLE) }
    var checkAmount by remember { mutableStateOf(0.0) }
    var tipPercentage by remember { mutableStateOf(AppConstants.DEFAULT_TIP_PERCENTAGE) }

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.HOME
        ) {
            composable(NavigationRoutes.HOME) {
                HomeScreen(
                    checkAmount = checkAmount,
                    onCheckAmountChange = { checkAmount = it },
                    numberOfPeople = numberOfPeople,
                    tipPercentage = tipPercentage,
                    onTipPercentageChange = { tipPercentage = it },
                    navController = navController
                )
            }

            composable(NavigationRoutes.SELECT_PEOPLE) {
                SelectPeopleScreen(
                    currentSelection = numberOfPeople,
                    onSelect = { selected ->
                        numberOfPeople = selected
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(checkAmount: Double,
               onCheckAmountChange: (Double) -> Unit,
               numberOfPeople: Int,
               tipPercentage: Int,
               onTipPercentageChange: (Int) -> Unit,
               navController: NavController) {
    // ====================================
    // Properties
    // ====================================
    var amountText by remember(checkAmount) {
        mutableStateOf(if (checkAmount > 0.0) checkAmount.toString() else "")
    }
    var amountIsFocused by remember { mutableStateOf(false) }

    val currencySymbol = remember { getCurrencySymbol() }

    val totalPerPerson by remember(checkAmount, numberOfPeople, tipPercentage) {
        derivedStateOf {
            val peopleCount = numberOfPeople.toDouble()
            val tipSelection = tipPercentage.toDouble()

            val tipValue = checkAmount / 100 * tipSelection
            val grandTotal = checkAmount + tipValue
            grandTotal / peopleCount
        }
    }

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.safeContentPadding(),
        topBar = {
            TopAppBar(
                title = { Text("WeSplit") },
                actions = {
                    if (amountIsFocused) {
                        TextButton(onClick = {
                            focusManager.clearFocus()
                        }) {
                            Text("Done")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = amountText,
                        onValueChange = { newValue ->
                            // Allow empty string, valid decimal numbers with up to 2 decimal places
                            // Reject lone decimal point or invalid formats
                            if (newValue.isEmpty() || newValue.matches(Regex("^\\d+(\\.\\d{0,2})?$"))) {
                                amountText = newValue
                                onCheckAmountChange(newValue.toDoubleOrNull() ?: 0.0)
                            }
                        },
                        label = { Text("Amount") },
                        leadingIcon = { Text(currencySymbol) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        modifier = Modifier.onFocusChanged { focusState ->
                            amountIsFocused = focusState.isFocused
                        },
                        singleLine = true
                    )

                    ListItem(
                        headlineContent = { Text("Number of people") },
                        supportingContent = { Text("$numberOfPeople people") },
                        trailingContent = {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                        },
                        modifier = Modifier.clickable {
                            navController.navigate(NavigationRoutes.SELECT_PEOPLE)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "How much tip do you want to leave?",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AppConstants.TIP_PERCENTAGES.forEachIndexed { index, percentage ->
                            SegmentedButton(
                                selected = tipPercentage == percentage,
                                onClick = {
                                    onTipPercentageChange(percentage)
                                },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = AppConstants.TIP_PERCENTAGES.size
                                )
                            ) {
                                Text("$percentage%")
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Amount per person",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = formatCurrency(totalPerPerson),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPeopleScreen(
    currentSelection: Int,
    onSelect: (Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Number of people") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(AppConstants.PEOPLE_RANGE.toList()) { number ->
                ListItem(
                    headlineContent = { Text("$number people") },
                    trailingContent = {
                        if (number == currentSelection) {
                            Icon(Icons.Default.Check, contentDescription = "Selected")
                        }
                    },
                    modifier = Modifier.clickable {
                        onSelect(number)
                        onNavigateBack()
                    }
                )
            }
        }
    }
}