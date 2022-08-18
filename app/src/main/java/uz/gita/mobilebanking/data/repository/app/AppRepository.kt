package uz.gita.mobilebanking.data.repository.app

import uz.gita.mobilebanking.data.local.model.app.LanguageData

interface AppRepository {

    suspend fun isFirstLaunch(): Boolean
    suspend fun dismissFirstLaunch()

    suspend fun getTrustedState(): Boolean
    suspend fun setTrustedState(state: Boolean)

    suspend fun getPinCode(): String
    suspend fun savePinCode(code: String)

    suspend fun getFingerPrintState(): Boolean
    suspend fun saveFingerPrintState(state: Boolean)

    suspend fun isUserAvailable() :Boolean
    suspend fun dismissUser()

    suspend fun currentLanguage(): String
    suspend fun setLanguage(code: String)
    suspend fun languagesList(): List<LanguageData>

}