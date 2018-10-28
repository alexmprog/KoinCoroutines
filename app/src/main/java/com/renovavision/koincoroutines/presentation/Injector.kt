package com.renovavision.koincoroutines.presentation

import android.arch.persistence.room.Room
import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.renovavision.koincoroutines.data.database.AppDatabase
import com.renovavision.koincoroutines.data.database.PostDao
import com.renovavision.koincoroutines.data.network.PostApiService
import com.renovavision.koincoroutines.data.repository.PostRepositoryImpl
import com.renovavision.koincoroutines.domain.repository.PostRepository
import com.renovavision.koincoroutines.domain.usecase.GetPostsUseCase
import com.renovavision.koincoroutines.presentation.post.PostListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val appModule: Module = module {
    viewModel { PostListViewModel(get()) }
}

val domainModule: Module = module {
    single { PostRepositoryImpl(get(), get()) as PostRepository }
    factory { GetPostsUseCase(get()) }
}

val dataModule: Module = module {
    single { provideRetrofit() }
    single { createWebService<PostApiService>(get()) }
    single { provideDatabase(androidApplication()) }
    single { providePostDao(get()) }
}

internal fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

internal inline fun <reified T> createWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

internal fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
        .fallbackToDestructiveMigration()
        .build()
}

internal fun providePostDao(database: AppDatabase): PostDao = database.postDao()