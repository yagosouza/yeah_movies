package com.yagosouza.yeahmovies.di

import com.yagosouza.yeahmovies.repository.HomeDataSource
import com.yagosouza.yeahmovies.repository.HomeDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun provideHomeDataSource(dataSource: HomeDataSourceImpl): HomeDataSource
}