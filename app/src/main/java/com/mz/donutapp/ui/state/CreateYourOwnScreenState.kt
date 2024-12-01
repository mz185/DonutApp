package com.mz.donutapp.ui.state

import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting

/**
 * Created by Marinos Zinonos on 01/12/2024.
 */

data class CreateYourOwnScreenState(
    val frostings: List<Frosting> = emptyList(),
    val fillings: List<Filling> = emptyList(),
    val error: String? = null
)