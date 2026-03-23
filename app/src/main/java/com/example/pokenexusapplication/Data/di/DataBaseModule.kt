package com.example.pokenexusapplication.Data.di

import android.content.Context
import androidx.room.Room
import com.example.pokenexusapplication.Data.LocalData.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun DataBaseCreator(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "Base de datos de pokemon"
        ).fallbackToDestructiveMigration().build()
    }
}