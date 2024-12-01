package com.mz.donutapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mz.donutapp.ui.viewmodel.HomeViewModel
import com.mz.donutapp.ui.viewmodel.SharedDonutViewModel

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@Composable
fun HomeScreen(
    sharedViewModel: SharedDonutViewModel,
    onCreateYourOwnClick: () -> Unit,
    onFinishOrdering: () -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val homeScreenState by viewModel.homeScreenState.collectAsState()
    val selectedCombination by sharedViewModel.selectedCombination.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        if (homeScreenState.error != null) {
            Text(
                text = "Error: ${homeScreenState.error}",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = onCreateYourOwnClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Your Own")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select a Donut Combination:", style = MaterialTheme.typography.headlineMedium)
        homeScreenState.donutCombinationEntities.forEach { combination ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = selectedCombination == combination,
                    onClick = { sharedViewModel.selectCombination(combination) }
                )
                Text(
                    text = "${combination.frosting?.name} +" +
                            " ${combination.filling?.name} -" +
                            " €${combination.price}",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onFinishOrdering,
            enabled = selectedCombination != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finish Ordering")
        }
    }
}