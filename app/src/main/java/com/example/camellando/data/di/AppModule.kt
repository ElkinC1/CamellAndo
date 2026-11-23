package com.example.camellando.data.di

import android.content.ContentResolver
import android.content.Context
import com.example.camellando.data.local.PostLocalDataSource
import com.example.camellando.data.repository.AuthRepository
import com.example.camellando.data.repository.ProfileRepository
import com.example.camellando.data.repository.ServicePostRepository
import com.example.camellando.data.repository.StorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = AppContainer.authRepository

    @Provides
    @Singleton
    fun provideServicePostRepository(): ServicePostRepository = AppContainer.servicePostRepository

    @Provides
    @Singleton
    fun provideStorageRepository(): StorageRepository = AppContainer.storageRepository

    @Provides
    @Singleton
    fun provideProfileRepository(): ProfileRepository = AppContainer.profileRepository

    @Provides
    @Singleton
    fun providePostLocalDataSource(
        @ApplicationContext context: Context
    ): PostLocalDataSource = PostLocalDataSource(context)

    @Provides
    @Singleton
    fun provideContentResolver(
        @ApplicationContext context: Context
    ): ContentResolver = context.contentResolver
}
