package uz.gita.mobilebanking.data.local

import android.content.Context
import uz.gita.mobilebanking.data.local.model.app.AppLanguages
import uz.gita.mobilebanking.utils.SharedPreference
import javax.inject.Inject

class AppSharedPreferences
@Inject constructor(context: Context) : SharedPreference(context) {

    // Auth
    var tempToken: String by StringPreference("")
    var accessToken: String by StringPreference("")
    var refreshToken: String by StringPreference("")

    // App
    var appLanguage: String by StringPreference(AppLanguages.UZ.value)
    var isFirstLaunch: Boolean by BooleanPreference(true)
    var isTrusted: Boolean by BooleanPreference(false)
    var pinCode: String by StringPreference("")
    var fingerPrintState: Boolean by BooleanPreference(false)


}