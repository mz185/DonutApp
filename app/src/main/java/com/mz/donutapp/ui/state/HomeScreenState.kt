package com.mz.donutapp.ui.state

import com.mz.donutapp.domain.entity.DonutCombinationEntity

/**
 * Created by Marinos Zinonos on 01/12/2024.
 */

data class HomeScreenState(
    val donutCombinationEntities: List<DonutCombinationEntity> = emptyList(),
    val error: String? = null
)