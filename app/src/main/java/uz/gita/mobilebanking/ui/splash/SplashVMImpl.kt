package uz.gita.mobilebanking.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.mobilebanking.data.local.model.app.SplashData
import uz.gita.mobilebanking.domain.usecase.splash.SplashUC
import javax.inject.Inject

@HiltViewModel
class SplashVMImpl @Inject constructor(
    private val useCase: SplashUC,
    private val direction: SplashDirection
) : ViewModel(), SplashVM {

    private var _state = MutableStateFlow(SplashContract.State())
    override val state: StateFlow<SplashContract.State> = _state.asStateFlow()

    init {
        setLanguage()
        navigate()
    }

    private fun setLanguage() {
        viewModelScope.launch {
            useCase
                .currentLanguage()
                .collectLatest { language -> reduce { it.copy(language = language) } }
        }
    }

    private fun navigate() {
        viewModelScope.launch {
            delay(2000)
            useCase.navigateNextScreen().collectLatest { splash ->
                when (splash) {
                    SplashData.SECURITY -> direction.navigateToSecurityScreen()
                    SplashData.MAIN -> direction.navigateToContentScreen()
                    SplashData.SIGN_IN -> direction.navigateToSignInScreen()
                    SplashData.LANGUAGE -> direction.navigateToLanguageScreen()
                }
            }
        }
    }

    private fun reduce(content: (old: SplashContract.State) -> SplashContract.State) {
        val oldState = _state.value
        val newState = content(oldState)
        _state.value = newState
    }

}