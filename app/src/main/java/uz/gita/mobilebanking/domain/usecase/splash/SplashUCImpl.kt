package uz.gita.mobilebanking.domain.usecase.splash

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import uz.gita.mobilebanking.data.local.model.app.SplashData
import uz.gita.mobilebanking.data.repository.app.AppRepository
import javax.inject.Inject

class SplashUCImpl @Inject constructor(
    private val repository: AppRepository
) : SplashUC {

    override fun currentLanguage() = flow<String> {
        emit(repository.currentLanguage())
    }.flowOn(Dispatchers.IO)

    override fun navigateNextScreen() = flow<SplashData> {
        when (repository.isFirstLaunch()) {
            false -> {
                Timber.d("isFirstLaunch: false")
                if (repository.isUserAvailable()) {
                    if (repository.getTrustedState()) {
                        if (repository.getPinCode().isNotEmpty()) emit(SplashData.SECURITY)
                        else emit(SplashData.MAIN)
                    } else emit(SplashData.SIGN_IN)
                } else emit(SplashData.SIGN_IN)
            }
            true -> {
                Timber.d("isFirstLaunch: true")
                emit(SplashData.LANGUAGE)
            }
        }
    }.flowOn(Dispatchers.IO)

}