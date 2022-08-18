package uz.gita.mobilebanking.ui.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.mobilebanking.data.local.model.app.AppLanguages
import uz.gita.mobilebanking.domain.usecase.language.LanguageUC
import uz.gita.mobilebankingMBF.R
import javax.inject.Inject

@HiltViewModel
class LanguageVMImpl @Inject constructor(
    private val useCase: LanguageUC,
    private val direction: LanguageDirection
) : ViewModel(), LanguageVM {

    private var _state = MutableStateFlow(LanguageContract.State())
    override val state: StateFlow<LanguageContract.State> = _state.asStateFlow()

    init {
        setCurrentLanguage()
        getLanguages()
    }

    override fun onEvent(intent: LanguageContract.Intent) {
        when (intent) {
            is LanguageContract.Intent.Next -> {
                viewModelScope.launch {
                    useCase
                        .setAppLanguage(intent.selectedLanguage)
                        .collectLatest { reduce { it.copy(currentLanguage = intent.selectedLanguage) } }
                }
                direction.navigateToOnBoardingScreen()
            }
            is LanguageContract.Intent.Selected -> {
                setUILanguage(intent.selectedLanguage)
            }
        }
    }

    private fun setCurrentLanguage() {
        viewModelScope.launch {
            useCase
                .currentLanguage()
                .collectLatest { language -> setUILanguage(language) }
        }
    }

    private fun getLanguages() {
        viewModelScope.launch {
            useCase
                .getAppLanguages()
                .collectLatest { languages -> reduce { it.copy(languages = languages) } }
        }
    }

    private fun setUILanguage(language: String) {
        reduce { it.copy(currentLanguage = language) }
        when (language) {
            AppLanguages.UZ.value -> reduce {
                it.copy(
                    titleText = R.string.select_language_uz,
                    nextButtonText = R.string.next_uz
                )
            }
            AppLanguages.RU.value -> reduce {
                it.copy(
                    titleText = R.string.select_language_ru,
                    nextButtonText = R.string.next_ru
                )
            }
            AppLanguages.EN.value -> reduce {
                it.copy(
                    titleText = R.string.select_language_en,
                    nextButtonText = R.string.next_en
                )
            }
        }
    }

    private fun reduce(content: (old: LanguageContract.State) -> LanguageContract.State) {
        val oldState = _state.value
        val newState = content(oldState)
        _state.value = newState
    }

}