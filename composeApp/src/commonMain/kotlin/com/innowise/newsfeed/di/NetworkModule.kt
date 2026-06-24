package com.innowise.newsfeed.di

import com.innowise.newsfeed.data.network.KtorApiClient
import com.innowise.newsfeed.data.network.createHttpClient
import com.innowise.newsfeed.data.repository.RemoteNewsRepositoryImpl
import com.innowise.newsfeed.domain.repository.RemoteNewsRepository
import org.koin.dsl.module

val networkModule = module {
    single { createHttpClient() }
    single { KtorApiClient(get()) }
    single<RemoteNewsRepository> { RemoteNewsRepositoryImpl(get()) }
}