package uz.gita.mobilebanking.data.repository.app

import uz.gita.mobilebanking.data.local.AppSharedPreferences
import uz.gita.mobilebanking.data.local.model.app.AppLanguages
import uz.gita.mobilebanking.data.local.model.app.LanguageData
import uz.gita.mobilebankingMBF.R
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val preferences: AppSharedPreferences
) : AppRepository {

    override suspend fun isFirstLaunch(): Boolean = preferences.isFirstLaunch
    override suspend fun dismissFirstLaunch() {
        preferences.isFirstLaunch = false
    }

    override suspend fun getTrustedState(): Boolean = preferences.isTrusted
    override suspend fun setTrustedState(state: Boolean) {
        preferences.isTrusted = state
    }

    override suspend fun getPinCode(): String = preferences.pinCode
    override suspend fun savePinCode(code: String) {
        preferences.pinCode = code
    }

    override suspend fun getFingerPrintState(): Boolean = preferences.fingerPrintState
    override suspend fun saveFingerPrintState(state: Boolean) {
        preferences.fingerPrintState = state
    }

    override suspend fun isUserAvailable(): Boolean = preferences.accessToken.isNotEmpty()
    override suspend fun dismissUser() {
        preferences.isTrusted = false
        preferences.fingerPrintState = false
        preferences.pinCode = ""
        preferences.tempToken = ""
        preferences.accessToken = ""
        preferences.refreshToken = ""
    }

    override suspend fun currentLanguage(): String = preferences.appLanguage
    override suspend fun languagesList(): List<LanguageData> = listOf(
        LanguageData(
            id = 1,
            icon = "\uD83C\uDDFA\uD83C\uDDFF",
            language = R.string.uzbek,
            code = AppLanguages.UZ.value,
            isChecked = true
        ),
        LanguageData(
            id = 2,
            icon = "\uD83C\uDDF7\uD83C\uDDFA",
            language = R.string.russian,
            code = AppLanguages.RU.value,
        ),
        LanguageData(
            id = 3,
            icon = "\uD83C\uDDEC\uD83C\uDDE7",
            language = R.string.english,
            code = AppLanguages.EN.value,
        )
    )

    override suspend fun setLanguage(code: String) {
        preferences.appLanguage = code
    }

}