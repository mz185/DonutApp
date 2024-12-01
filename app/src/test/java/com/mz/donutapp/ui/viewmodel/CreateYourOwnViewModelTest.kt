package com.mz.donutapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting
import com.mz.donutapp.domain.enum.OptionsType
import com.mz.donutapp.domain.usecase.GetOptionsUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

class CreateYourOwnViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getOptionsUseCaseMock: GetOptionsUseCase = mockk()

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
        every { getOptionsUseCaseMock<Frosting>(OptionsType.FROSTING) } returns Result.success(frostings)
        every { getOptionsUseCaseMock<Filling>(OptionsType.FILLING) } returns Result.success(fillings)

        // When
        val createYourOwnViewModel = CreateYourOwnViewModel(getOptionsUseCaseMock)

        // Then
        val createYourOwnScreenState = createYourOwnViewModel.createYourOwnScreenState.value
        assertThat(createYourOwnScreenState.error).isNull()
        assertThat(createYourOwnScreenState.frostings).isEqualTo(frostings)
        assertThat(createYourOwnScreenState.fillings).isEqualTo(fillings)
    }

    @Test
    fun failedFrostingDataFetch() {
        // Given
        val e = Exception("Failed to fetch frostings")
        every { getOptionsUseCaseMock<Frosting>(OptionsType.FROSTING) } returns Result.failure(e)
        every { getOptionsUseCaseMock<Filling>(OptionsType.FILLING) } returns Result.success(any())

        // When
        val createYourOwnViewModel = CreateYourOwnViewModel(getOptionsUseCaseMock)

        // Then
        val createYourOwnScreenState = createYourOwnViewModel.createYourOwnScreenState.value
        assertThat(createYourOwnScreenState.frostings).isEqualTo(emptyList())
        assertThat(createYourOwnScreenState.fillings).isEqualTo(emptyList())
        assertThat(createYourOwnScreenState.error).isNotNull()
            .isEqualTo("Failed to load data: ${e.message}")
    }

    @Test
    fun failedFillingDataFetch() {
        // Given
        val e = Exception("Failed to fetch fillings")
        every { getOptionsUseCaseMock<Frosting>(OptionsType.FROSTING) } returns Result.success(any())
        every { getOptionsUseCaseMock<Filling>(OptionsType.FILLING) } returns Result.failure(e)

        // When
        val createYourOwnViewModel = CreateYourOwnViewModel(getOptionsUseCaseMock)

        // Then
        val createYourOwnScreenState = createYourOwnViewModel.createYourOwnScreenState.value
        assertThat(createYourOwnScreenState.frostings).isEqualTo(emptyList())
        assertThat(createYourOwnScreenState.fillings).isEqualTo(emptyList())
        assertThat(createYourOwnScreenState.error).isNotNull()
            .isEqualTo("Failed to load data: ${e.message}")
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
        val createYourOwnScreenState = createYourOwnViewModel.createYourOwnScreenState.value
        assertThat(createYourOwnScreenState.frostings).isEqualTo(emptyList())
        assertThat(createYourOwnScreenState.fillings).isEqualTo(emptyList())
        assertThat(createYourOwnScreenState.error).isNotNull()
            .isEqualTo("Failed to load data: ${frostingsException.message}")
    }
}