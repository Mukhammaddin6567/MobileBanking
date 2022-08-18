package uz.gita.mobilebanking.ui.fingerprint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.mobilebanking.domain.usecase.fingerprint.FingerprintUC
import javax.inject.Inject

@HiltViewModel
class FingerprintVMImpl @Inject constructor(
    private val fingerprintUC: FingerprintUC,
    private val direction: FingerprintDirection
) : ViewModel(), FingerprintVM {

    private var _sideEffect: ((FingerprintContract.SideEffect) -> Unit)? = null

    override fun onEvent(intent: FingerprintContract.Intent) {
        when (intent) {
            FingerprintContract.Intent.TURN_ON -> _sideEffect?.invoke(FingerprintContract.SideEffect.LAUNCH)
            FingerprintContract.Intent.SKIP -> {
                direction.navigateToContentScreen()
                _sideEffect = null
            }
            FingerprintContract.Intent.SUCCESS -> {
                viewModelScope.launch {
                    fingerprintUC
                        .setFingerPrintState(true)
                        .collectLatest {
                            direction.navigateToContentScreen()
                            _sideEffect = null
                        }
                }
            }
            FingerprintContract.Intent.BACK -> {
                direction.navigateToSignUpScreen()
                _sideEffect = null
            }
        }
    }

    override fun sideEffect(sideEffect: (FingerprintContract.SideEffect) -> Unit) {
        _sideEffect = sideEffect
    }

}