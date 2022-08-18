package uz.gita.mobilebanking.ui.language

import kotlinx.coroutines.flow.StateFlow

interface LanguageVM {

    val state: StateFlow<LanguageContract.State>
    fun onEvent(intent: LanguageContract.Intent)

}