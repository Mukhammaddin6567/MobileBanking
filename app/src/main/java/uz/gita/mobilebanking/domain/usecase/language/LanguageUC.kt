package uz.gita.mobilebanking.domain.usecase.language

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.local.model.app.LanguageData

interface LanguageUC {

    fun getAppLanguages(): Flow<List<LanguageData>>

    fun currentLanguage(): Flow<String>

    fun setAppLanguage(language: String): Flow<Unit>

}