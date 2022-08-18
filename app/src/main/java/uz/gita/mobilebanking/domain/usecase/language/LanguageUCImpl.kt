package uz.gita.mobilebanking.domain.usecase.language

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import uz.gita.mobilebanking.data.repository.app.AppRepository
import javax.inject.Inject

class LanguageUCImpl @Inject constructor(
    private val appRepository: AppRepository
) : LanguageUC {

    override fun getAppLanguages() = flow {
        emit(appRepository.languagesList())
    }.flowOn(Dispatchers.IO)

    override fun currentLanguage() = flow<String> {
        emit(appRepository.currentLanguage())
    }.flowOn(Dispatchers.IO)

    override fun setAppLanguage(language: String) = flow<Unit> {
        emit(appRepository.setLanguage(language))
    }.flowOn(Dispatchers.IO)

}