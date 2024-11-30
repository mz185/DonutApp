package com.mz.donutapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mz.donutapp.domain.entity.DonutCombinationEntity
import com.mz.donutapp.presentation.viewmodel.HomeViewModel

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onCreateYourOwnClick: () -> Unit
) {
    val donutCombinations by viewModel.donutCombinationEntities.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        if (error != null) {
            Text(
                text = "Error: $error",
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

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(donutCombinations) { combination ->
                DonutItem(combination)
            }
        }
    }
}

@Composable
fun DonutItem(combination: DonutCombinationEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("${combination.frosting.name} + ${combination.filling.name}")
        Text("Price: $${combination.price}")
    }
}