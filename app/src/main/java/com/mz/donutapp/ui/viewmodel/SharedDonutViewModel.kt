package com.mz.donutapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting
import com.mz.donutapp.domain.entity.DonutCombinationEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@HiltViewModel
class SharedDonutViewModel @Inject constructor() : ViewModel() {

    private val _selectedCombination = MutableStateFlow<DonutCombinationEntity?>(null)
    val selectedCombination: StateFlow<DonutCombinationEntity?> = _selectedCombination

    fun selectCombination(combination: DonutCombinationEntity?) {
        _selectedCombination.value = combination
    }

    fun selectFrosting(frosting: Frosting) {
        updateCombination(frosting = frosting)
    }

    fun selectFilling(filling: Filling) {
        updateCombination(filling = filling)
    }

    fun clearSelections() {
        _selectedCombination.value = null
    }

    private fun updateCombination(frosting: Frosting? = null, filling: Filling? = null) {
        val selectedCombination = _selectedCombination.value ?: DonutCombinationEntity()
        _selectedCombination.value = selectedCombination.copy(
            frosting = frosting ?: selectedCombination.frosting,
            filling = filling ?: selectedCombination.filling
        )
        updatePrice()
    }

    private fun updatePrice() {
        val selectedCombination = _selectedCombination.value ?: return
        val frostingPrice = selectedCombination.frosting?.price ?: 0.0
        val fillingPrice = selectedCombination.filling?.price ?: 0.0
        val price = frostingPrice + fillingPrice
        _selectedCombination.value = selectedCombination.copy(price = price)
    }
}