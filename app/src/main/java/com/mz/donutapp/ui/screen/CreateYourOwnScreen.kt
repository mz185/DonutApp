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
import com.mz.donutapp.ui.viewmodel.CreateYourOwnViewModel
import com.mz.donutapp.ui.viewmodel.SharedDonutViewModel

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@Composable
fun CreateYourOwnScreen(
    sharedViewModel: SharedDonutViewModel,
    onFinishOrdering: () -> Unit
) {
    val viewModel: CreateYourOwnViewModel = hiltViewModel()
    val frostings by viewModel.frostings.collectAsState()
    val fillings by viewModel.fillings.collectAsState()
    val error by viewModel.error.collectAsState()
    val selectedCombination by sharedViewModel.selectedCombination.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        if (error != null) {
            Text(
                text = "Error: $error",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Text("Select Frosting:")
        frostings.forEach { frosting ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = selectedCombination?.frosting == frosting,
                    onClick = { sharedViewModel.selectFrosting(frosting) }
                )
                Text(
                    text = "${frosting.name} - €${frosting.price}",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Filling:")
        fillings.forEach { filling ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = selectedCombination?.filling == filling,
                    onClick = { sharedViewModel.selectFilling(filling) }
                )
                Text(
                    text = "${filling.name} - €${filling.price}",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Total Price: €${selectedCombination?.price ?: 0}",
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onFinishOrdering,
            enabled = selectedCombination?.frosting != null && selectedCombination?.filling != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finish Ordering")
        }
    }
}