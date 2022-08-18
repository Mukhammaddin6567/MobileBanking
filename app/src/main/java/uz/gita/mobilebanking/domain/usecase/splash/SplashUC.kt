package uz.gita.mobilebanking.domain.usecase.splash

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.local.model.app.SplashData

interface SplashUC {

    fun currentLanguage(): Flow<String>
    fun navigateNextScreen(): Flow<SplashData>

}