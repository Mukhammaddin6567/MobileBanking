package uz.gita.mobilebanking.di

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import uz.gita.mobilebanking.data.local.AppSharedPreferences
import uz.gita.mobilebanking.data.remote.api.AuthApi
import uz.gita.mobilebankingMBF.BuildConfig.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @[Provides Singleton]
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @[Provides Singleton]
    fun provideHeaderInterceptor(preferences: AppSharedPreferences) = Interceptor { chain ->
        val request = chain.request()
        val url = chain.request().url.toString()
        Timber.d("url: $url")
        if (url.contains("verify")) {
            val newRequest = chain.request().newBuilder()
                .addHeader(AUTHORIZATION, "$BEARER ${preferences.tempToken}")
                .build()
            return@Interceptor chain.proceed(newRequest)
        }
        if (url.contains("card")) {
            val newRequest = chain.request().newBuilder()
                .addHeader(AUTHORIZATION, "$BEARER ${preferences.accessToken}")
                .build()
            return@Interceptor chain.proceed(newRequest)
        }
        return@Interceptor chain.proceed(request)
    }

    @[Provides Singleton]
    fun provideChuckInterceptor(@ApplicationContext context: Context) = ChuckInterceptor(context)

    @[Provides Singleton]
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        preferences: AppSharedPreferences
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(provideHttpLoggingInterceptor())
        .addInterceptor(provideHeaderInterceptor(preferences))
        .addInterceptor(provideChuckInterceptor(context))
        .build()


    @[Provides Singleton]
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @[Provides Singleton]
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

}