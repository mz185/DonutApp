package com.mz.donutapp.di

import com.mz.donutapp.data.repo.DonutRepository
import com.mz.donutapp.domain.usecase.GetDonutCombinationsUseCase
import com.mz.donutapp.domain.usecase.GetOptionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetOptionsUseCase(repository: DonutRepository): GetOptionsUseCase {
        return GetOptionsUseCase(repository)
    }

    @Provides
    fun provideGetDonutCombinationsUseCase(): GetDonutCombinationsUseCase {
        return GetDonutCombinationsUseCase()
    }
}