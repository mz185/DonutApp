package com.mz.donutapp.domain.usecase

import com.mz.donutapp.data.repo.DonutRepository
import com.mz.donutapp.domain.enum.OptionsType

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

class GetOptionsUseCase(private val repository: DonutRepository) {

    suspend operator fun <T> invoke(type: OptionsType): Result<List<T>> {
        return repository.getOptions(type)
    }
}