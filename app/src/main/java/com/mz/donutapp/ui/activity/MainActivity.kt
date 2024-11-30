package com.mz.donutapp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mz.donutapp.ui.nav.DonutAppNavGraph
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
                DonutAppNavGraph()
            }
        }
    }
}