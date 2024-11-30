package com.mz.donutapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting
import com.mz.donutapp.domain.entity.DonutCombinationEntity
import com.mz.donutapp.domain.enum.OptionsType
import com.mz.donutapp.domain.usecase.GetDonutCombinationsUseCase
import com.mz.donutapp.domain.usecase.GetOptionsUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getOptionsUseCaseMock: GetOptionsUseCase = mockk()
    private val getDonutCombinationsUseCaseMock: GetDonutCombinationsUseCase = mockk()

    @Test
    fun successfulDataFetch() {
        // Given
        val frostings = listOf(
            Frosting(name = "Vanilla", price = 1.0),
            Frosting(name = "Chocolate", price = 1.5)
        )
        val fillings = listOf(
            Filling(name = "Jam", price = 0.5),
            Filling(name = "Custard", price = 0.8)
        )
        val combinations = listOf(
            DonutCombinationEntity(frosting = frostings[0], filling = fillings[0], price = 1.5),
            DonutCombinationEntity(frosting = frostings[1], filling = fillings[1], price = 2.3)
        )

        every { getOptionsUseCaseMock<Frosting>(OptionsType.FROSTING) } returns Result.success(frostings)
        every { getOptionsUseCaseMock<Filling>(OptionsType.FILLING) } returns Result.success(fillings)
        every { getDonutCombinationsUseCaseMock(frostings, fillings) } returns combinations

        // When
        val homeViewModel = HomeViewModel(getOptionsUseCaseMock, getDonutCombinationsUseCaseMock)

        // Then
        assertThat(homeViewModel.error.value).isNull()
        assertThat(homeViewModel.donutCombinationEntities.value).isEqualTo(combinations)
    }

    @Test
    fun failedFrostingDataFetch() {
        // Given
        val e = Exception("Failed to fetch options")
        every { getOptionsUseCaseMock<Frosting>(OptionsType.FROSTING) } returns Result.failure(e)
        every { getOptionsUseCaseMock<Filling>(OptionsType.FILLING) } returns Result.success(any())

        // When
        val homeViewModel = HomeViewModel(getOptionsUseCaseMock, getDonutCombinationsUseCaseMock)

        // Then
        assertThat(homeViewModel.donutCombinationEntities.value).isEqualTo(emptyList())
        assertThat(homeViewModel.error.value).isEqualTo("Failed to load data: ${e.message}")
    }

    @Test
    fun failedFillingDataFetch() {
        // Given
        val e = Exception("Failed to fetch options")
        every { getOptionsUseCaseMock<Frosting>(OptionsType.FROSTING) } returns Result.success(any())
        every { getOptionsUseCaseMock<Filling>(OptionsType.FILLING) } returns Result.failure(e)

        // When
        val homeViewModel = HomeViewModel(getOptionsUseCaseMock, getDonutCombinationsUseCaseMock)

        // Then
        assertThat(homeViewModel.donutCombinationEntities.value).isEqualTo(emptyList())
        assertThat(homeViewModel.error.value).isEqualTo("Failed to load data: ${e.message}")
    }

    @Test
    fun failedFrostingAndFillingDataFetch() {
        // Given
        val frostingsException = Exception("Failed to fetch frostings")
        val fillingsException = Exception("Failed to fetch fillings")
        every { getOptionsUseCaseMock<Frosting>(OptionsType.FROSTING) } returns Result.failure(frostingsException)
        every { getOptionsUseCaseMock<Filling>(OptionsType.FILLING) } returns Result.failure(fillingsException)

        // When
        val createYourOwnViewModel = CreateYourOwnViewModel(getOptionsUseCaseMock)

        // Then
        assertThat(createYourOwnViewModel.frostings.value).isEqualTo(emptyList())
        assertThat(createYourOwnViewModel.fillings.value).isEqualTo(emptyList())
        assertThat(createYourOwnViewModel.error.value).isNotNull()
            .isEqualTo("Failed to load data: ${frostingsException.message}")
    }
}