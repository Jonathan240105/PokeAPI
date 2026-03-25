package com.example.pokenexusapplication.Data.RemoteData

import com.example.pokenexusapplication.Data.RemoteData.Variables.APIUrls
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object pokeAPI {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(APIUrls.Url_PokeApi)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideDataInterface(retrofit: Retrofit): DataInterface =
        retrofit.create(DataInterface::class.java)
}