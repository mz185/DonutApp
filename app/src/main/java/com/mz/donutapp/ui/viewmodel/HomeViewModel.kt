package com.mz.donutapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting
import com.mz.donutapp.domain.enum.OptionsType
import com.mz.donutapp.domain.usecase.GetDonutCombinationsUseCase
import com.mz.donutapp.domain.usecase.GetOptionsUseCase
import com.mz.donutapp.ui.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getOptionsUseCase: GetOptionsUseCase,
    private val getDonutCombinationsUseCase: GetDonutCombinationsUseCase
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState

    init {
        fetchCombinations()
    }

    private fun fetchCombinations() {
        val frostingsResult = getOptionsUseCase<Frosting>(OptionsType.FROSTING)
        val fillingsResult = getOptionsUseCase<Filling>(OptionsType.FILLING)

        if (frostingsResult.isSuccess && fillingsResult.isSuccess) {
            val frostings = frostingsResult.getOrDefault(emptyList())
            val fillings = fillingsResult.getOrDefault(emptyList())
            _homeScreenState.update {
                it.copy(
                    donutCombinationEntities = getDonutCombinationsUseCase(frostings, fillings),
                    error = null
                )
            }
        } else {
            val e = frostingsResult.exceptionOrNull() ?: fillingsResult.exceptionOrNull()
            _homeScreenState.update {
                it.copy(error = "Failed to load data: ${e?.message}")
            }
        }
    }
}