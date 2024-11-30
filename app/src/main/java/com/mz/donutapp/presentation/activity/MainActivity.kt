package com.mz.donutapp.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.mz.donutapp.presentation.screen.HomeScreen
import com.mz.donutapp.ui.theme.DonutAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DonutAppTheme {
                HomeScreen(
                    viewModel = hiltViewModel(),
                    onCreateYourOwnClick = {
                        // Handle navigation to custom donut creation screen here
                    }
                )
            }
        }
    }
}