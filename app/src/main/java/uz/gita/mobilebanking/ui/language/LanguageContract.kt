package uz.gita.mobilebanking.ui.language

import androidx.compose.runtime.Immutable
import uz.gita.mobilebanking.data.local.model.app.LanguageData
import uz.gita.mobilebankingMBF.R

class LanguageContract {

    @Immutable
    data class State(
        val titleText: Int = R.string.select_language_uz,
        val nextButtonText: Int = R.string.next_uz,
        val currentLanguage: String = "",
        val languages: List<LanguageData> = emptyList()
    )

    sealed class Intent {
        data class Next(val selectedLanguage: String) : Intent()
        data class Selected(val selectedLanguage: String) : Intent()
    }

}