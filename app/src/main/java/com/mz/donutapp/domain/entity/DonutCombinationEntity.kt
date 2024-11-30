package com.mz.donutapp.domain.entity

import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

data class DonutCombinationEntity(
    val frosting: Frosting? = null,
    val filling: Filling? = null,
    val price: Double = 0.0
)