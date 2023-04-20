package com.example.plantidentification.feature_plant_identification.core.di

import android.content.Context
import com.example.plantidentification.feature_plant_identification.data.repository.MainRepositoryImpl
import com.example.plantidentification.feature_plant_identification.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideMainRepository(@ApplicationContext context: Context): MainRepository {
        return MainRepositoryImpl(context)
    }
}