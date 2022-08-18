package uz.gita.mobilebanking.di

import android.content.Context
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.android.compose.AppReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModoModule {

    @[Provides Singleton]
    fun provideModo(@ApplicationContext context: Context): Modo = Modo(AppReducer(context))

}