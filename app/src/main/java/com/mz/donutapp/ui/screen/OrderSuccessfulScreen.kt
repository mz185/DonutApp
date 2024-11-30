package com.mz.donutapp.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mz.donutapp.ui.viewmodel.SharedDonutViewModel

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@Composable
fun OrderSuccessfulScreen(
    sharedViewModel: SharedDonutViewModel,
    onBackToMenu: () -> Unit
) {
    val selectedCombination by sharedViewModel.selectedCombination.collectAsState()

    BackHandler(enabled = true) {
        onBackToMenu()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            if (selectedCombination != null) "Order Successful" else "No Order Selected",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        selectedCombination?.let { combination ->
            Text("You ordered: ${combination.frosting?.name} + ${combination.filling?.name}")
            Text("Total Price: $${combination.price}")
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(onClick = { onBackToMenu() }) {
            Text("Back to Menu")
        }
    }
}