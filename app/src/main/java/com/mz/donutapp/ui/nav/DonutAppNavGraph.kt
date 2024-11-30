package com.mz.donutapp.ui.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mz.donutapp.ui.screen.CreateYourOwnScreen
import com.mz.donutapp.ui.screen.HomeScreen
import com.mz.donutapp.ui.screen.OrderSuccessfulScreen
import com.mz.donutapp.ui.viewmodel.SharedDonutViewModel

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@Composable
fun DonutAppNavGraph() {
    val navController = rememberNavController()
    val sharedViewModel: SharedDonutViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                sharedViewModel = sharedViewModel,
                onCreateYourOwnClick = { navController.navigate("create_your_own") },
                onFinishOrdering = { navController.navigate("order_success") }
            )
        }
        composable("create_your_own") {
            CreateYourOwnScreen(
                sharedViewModel = sharedViewModel,
                onFinishOrdering = { navController.navigate("order_success") }
            )
        }
        composable("order_success") {
            OrderSuccessfulScreen(
                sharedViewModel = sharedViewModel,
                onBackToMenu = {
                    sharedViewModel.clearSelections()
                    navController.navigate("home") {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}