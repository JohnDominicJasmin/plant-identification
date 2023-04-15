package com.example.plantidentification.core.di

import com.example.plantidentification.data.repository.MainRepositoryImpl
import com.example.plantidentification.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideMainRepository(): MainRepository {
        return MainRepositoryImpl()
    }
}