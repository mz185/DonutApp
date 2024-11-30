package com.mz.donutapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting
import com.mz.donutapp.domain.entity.DonutCombinationEntity
import com.mz.donutapp.domain.enum.OptionsType
import com.mz.donutapp.domain.usecase.GetDonutCombinationsUseCase
import com.mz.donutapp.domain.usecase.GetOptionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getOptionsUseCase: GetOptionsUseCase,
    private val getDonutCombinationsUseCase: GetDonutCombinationsUseCase
) : ViewModel() {

    private val _donutCombinationEntities = MutableStateFlow<List<DonutCombinationEntity>>(emptyList())
    val donutCombinationEntities: StateFlow<List<DonutCombinationEntity>> = _donutCombinationEntities

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchCombinations()
    }

    private fun fetchCombinations() {
        val frostingsResult = getOptionsUseCase<Frosting>(OptionsType.FROSTING)
        val fillingsResult = getOptionsUseCase<Filling>(OptionsType.FILLING)

        if (frostingsResult.isSuccess && fillingsResult.isSuccess) {
            val frostings = frostingsResult.getOrDefault(emptyList())
            val fillings = fillingsResult.getOrDefault(emptyList())
            _donutCombinationEntities.value = getDonutCombinationsUseCase(frostings, fillings)
        } else {
            val e = frostingsResult.exceptionOrNull() ?: fillingsResult.exceptionOrNull()
            _error.value = "Failed to load data: ${e?.message}"
        }
    }
}