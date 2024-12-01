package com.mz.donutapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting
import com.mz.donutapp.domain.enum.OptionsType
import com.mz.donutapp.domain.usecase.GetOptionsUseCase
import com.mz.donutapp.ui.state.CreateYourOwnScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@HiltViewModel
class CreateYourOwnViewModel @Inject constructor(
    private val getOptionsUseCase: GetOptionsUseCase
) : ViewModel() {

    private val _createYourOwnScreenState = MutableStateFlow(CreateYourOwnScreenState())
    val createYourOwnScreenState: StateFlow<CreateYourOwnScreenState> = _createYourOwnScreenState

    init {
        fetchOptions()
    }

    private fun fetchOptions() {
        val frostingsResult = getOptionsUseCase<Frosting>(OptionsType.FROSTING)
        val fillingsResult = getOptionsUseCase<Filling>(OptionsType.FILLING)

        if (frostingsResult.isSuccess && fillingsResult.isSuccess) {
            val frostings = frostingsResult.getOrDefault(emptyList())
            val fillings = fillingsResult.getOrDefault(emptyList())

            _createYourOwnScreenState.update {
                it.copy(
                    frostings = frostings,
                    fillings = fillings,
                    error = null
                )
            }
        } else {
            val e = frostingsResult.exceptionOrNull() ?: fillingsResult.exceptionOrNull()
            _createYourOwnScreenState.update {
                it.copy(error = "Failed to load data: ${e?.message}")
            }
        }
    }
}