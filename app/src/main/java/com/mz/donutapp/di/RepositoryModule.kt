package com.mz.donutapp.di

import android.content.Context
import com.mz.donutapp.data.repo.DonutRepository
import com.mz.donutapp.data.repo.DonutRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDonutRepository(@ApplicationContext context: Context): DonutRepository {
        return DonutRepositoryImpl(context)
    }
}