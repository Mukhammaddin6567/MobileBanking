package uz.gita.mobilebanking.ui.privacy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.mobilebanking.domain.usecase.privacypolicy.PrivacyPolicyUC
import javax.inject.Inject

@HiltViewModel
class PrivacyPolicyVMImpl @Inject constructor(
    private val useCase: PrivacyPolicyUC,
    private val direction: PrivacyPolicyDirection
) : ViewModel(), PrivacyPolicyVM {

    private var _state = MutableStateFlow(PrivacyPolicyContract.State())
    override val state: StateFlow<PrivacyPolicyContract.State> = _state.asStateFlow()

    override fun onEvent(intent: PrivacyPolicyContract.Intent) {
        when (intent) {
            PrivacyPolicyContract.Intent.CHECK -> {
                reduce { it.copy(buttonAcceptStatus = !it.buttonAcceptStatus) }
            }
            PrivacyPolicyContract.Intent.ACCEPT -> {
                viewModelScope.launch {
                    useCase
                        .dismissFirstLaunch()
                        .collectLatest {
                            direction.navigateToSignInScreen()
                        }
                }

            }
        }
    }

    private fun reduce(content: (old: PrivacyPolicyContract.State) -> PrivacyPolicyContract.State) {
        val oldState = _state.value
        val newState = content(oldState)
        _state.value = newState
    }
}