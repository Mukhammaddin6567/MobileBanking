package uz.gita.mobilebanking.ui.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.gita.mobilebanking.domain.usecase.security.SecurityUC
import javax.inject.Inject

@HiltViewModel
class SecurityVMImpl @Inject constructor(
    private val useCase: SecurityUC,
    private val direction: SecurityDirection
) : ViewModel(), SecurityVM {

    private var _state = MutableStateFlow(SecurityContract.State())
    private var effect: ((SecurityContract.SideEffect) -> Unit)? = null
    override val state: StateFlow<SecurityContract.State> = _state.asStateFlow()

    init {
        setFingerprintState()
    }

    override fun onEvent(intent: SecurityContract.Intent) {
        when (intent) {
            is SecurityContract.Intent.Filled -> {
                viewModelScope.launch {
                    useCase
                        .checkPinCode(intent.code)
                        .collectLatest { state ->
                            Timber.d("isError vm: $state")
                            if (state) direction.navigateToContentScreen()
                            else reduce { it.copy(isError = !state) }
                        }
                }
            }
            is SecurityContract.Intent.Success -> direction.navigateToContentScreen()
            is SecurityContract.Intent.Fingerprint -> {
                effect?.invoke(SecurityContract.SideEffect.FINGERPRINT)
            }
            is SecurityContract.Intent.Backspace -> {
                if (_state.value.isError) reduce { it.copy(isError = false) }
            }
            is SecurityContract.Intent.LoginWithPassword -> direction.navigateToSignInScreen()
        }
    }

    override fun sideEffect(sideEffect: (SecurityContract.SideEffect) -> Unit) {
        effect = sideEffect
    }

    private fun setFingerprintState() {
        viewModelScope.launch {
            useCase
                .getFingerPrintState()
                .collectLatest { state ->
                    if (state) effect?.invoke(SecurityContract.SideEffect.FINGERPRINT)
                    reduce { it.copy(fingerprintState = state) }
                }
        }
    }

    private fun reduce(component: (old: SecurityContract.State) -> SecurityContract.State) {
        val oldState = _state.value
        val newState = component(oldState)
        _state.value = newState
    }

}