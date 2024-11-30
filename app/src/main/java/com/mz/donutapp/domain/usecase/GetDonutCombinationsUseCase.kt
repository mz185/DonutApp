package com.mz.donutapp.domain.usecase

import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting
import com.mz.donutapp.domain.entity.DonutCombinationEntity

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

class GetDonutCombinationsUseCase {

    operator fun invoke(frostings: List<Frosting>, fillings: List<Filling>): List<DonutCombinationEntity> {
        return frostings.flatMap { frosting ->
            fillings.map { filling ->
                DonutCombinationEntity(
                    frosting = frosting,
                    filling = filling,
                    price = frosting.price + filling.price
                )
            }
        }
    }
}