package com.mz.donutapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting
import com.mz.donutapp.domain.entity.DonutCombinationEntity
import com.mz.donutapp.ui.viewmodel.SharedDonutViewModel
import org.junit.Rule
import org.junit.Test

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

class SharedDonutViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sharedViewModel = SharedDonutViewModel()

    @Test
    fun selectCombination() {
        // Given
        val combination = DonutCombinationEntity(
            frosting = Frosting(name = "Vanilla", price = 1.0),
            filling = Filling(name = "Jam", price = 0.5),
            price = 1.5
        )

        // When
        sharedViewModel.selectCombination(combination)

        // Then
        assertThat(sharedViewModel.selectedCombination.value).isEqualTo(combination)
    }

    @Test
    fun updateFrosting() {
        // Given
        val frosting = Frosting(name = "Chocolate", price = 1.5)

        // When
        sharedViewModel.selectFrosting(frosting)

        // Then
        assertThat(sharedViewModel.selectedCombination.value?.frosting).isEqualTo(frosting)
        assertThat(sharedViewModel.selectedCombination.value?.price).isEqualTo(1.5)
    }

    @Test
    fun updateFilling() {
        // Given
        val filling = Filling(name = "Custard", price = 0.8)

        // When
        sharedViewModel.selectFilling(filling)

        // Then
        assertThat(sharedViewModel.selectedCombination.value?.filling).isEqualTo(filling)
        assertThat(sharedViewModel.selectedCombination.value?.price).isEqualTo(0.8)
    }

    @Test
    fun clearSelections() {
        // Given
        val combination = DonutCombinationEntity(
            frosting = Frosting(name = "Vanilla", price = 1.0),
            filling = Filling(name = "Jam", price = 0.5),
            price = 1.5
        )
        sharedViewModel.selectCombination(combination)
        assertThat(sharedViewModel.selectedCombination.value).isEqualTo(combination)

        // When
        sharedViewModel.clearSelections()

        // Then
        assertThat(sharedViewModel.selectedCombination.value).isNull()
    }

    @Test
    fun updatePrice() {
        // Given
        val frosting = Frosting(name = "Vanilla", price = 1.0)
        val filling = Filling(name = "Jam", price = 0.5)

        // When
        sharedViewModel.selectFrosting(frosting)

        // Then
        assertThat(sharedViewModel.selectedCombination.value?.price).isEqualTo(1.0)

        // When
        sharedViewModel.selectFilling(filling)

        // Then
        assertThat(sharedViewModel.selectedCombination.value?.price).isEqualTo(1.5)
    }
}