package uz.gita.mobilebanking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.mobilebanking.navigation.AppGraph
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GraphModule {

    @[Provides Singleton]
    fun provideAppGraph(): AppGraph = AppGraph()

}