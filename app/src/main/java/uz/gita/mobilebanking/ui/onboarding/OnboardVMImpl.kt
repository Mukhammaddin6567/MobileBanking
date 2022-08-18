package uz.gita.mobilebanking.ui.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardVMImpl @Inject constructor(
    private val direction: OnboardDirection
) : ViewModel(), OnboardVM {

    private var _sideEffect: ((OnboardContract.SideEffect) -> Unit)? = null

    override fun onEvent(intent: OnboardContract.Intent) {
        when (intent) {
            OnboardContract.Intent.SKIP -> {
                direction.navigateToPrivacyPolicyScreen()
            }
            OnboardContract.Intent.START -> {
                direction.navigateToPrivacyPolicyScreen()
            }
            OnboardContract.Intent.BACK -> {
                _sideEffect?.invoke(OnboardContract.SideEffect.FINISH)
                _sideEffect = null
            }
        }
    }

    override fun sideEffect(sideEffect: (OnboardContract.SideEffect) -> Unit) {
        _sideEffect = sideEffect
    }

}