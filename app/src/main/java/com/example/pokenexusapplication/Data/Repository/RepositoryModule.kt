package com.example.pokenexusapplication.Data.Repository

import com.example.pokenexusapplication.Data.Repository.Repository
import com.example.pokenexusapplication.Data.Repository.RepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(
        repositoryImp: RepositoryImp
    ): Repository
}