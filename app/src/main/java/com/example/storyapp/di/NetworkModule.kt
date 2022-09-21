package com.example.storyapp.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.storyapp.BuildConfig.BASE_URL
import com.example.storyapp.data.StoriesRemoteMediator
import com.example.storyapp.data.StoriesRepository
import com.example.storyapp.data.local.StoriesDatabase
import com.example.storyapp.data.remote.RemoteDataSource
import com.example.storyapp.data.remote.network.ApiService
import com.example.storyapp.domain.IStoriesRepository
import com.example.storyapp.utils.SharePreferences
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        val chuckerCollector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        val chuckerInterceptor = ChuckerInterceptor.Builder(androidContext())
            .collector(chuckerCollector)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()

        OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    factory { get<StoriesDatabase>().getAllStoriesDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            StoriesDatabase::class.java, "Stories.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val sharePreferencesModule = module {
    single { SharePreferences(get()) }
}

val repositoryModule = module {
    single { RemoteDataSource(get(), get()) }
    factory<IStoriesRepository> { StoriesRepository(get(),get(),get(),get()) }
}

val storiesRemoteMediatorModule = module {
    factory { StoriesRemoteMediator(get(), get(), get()) }
}