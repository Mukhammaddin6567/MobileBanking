package uz.gita.mobilebanking.ui.privacy

import kotlinx.coroutines.flow.StateFlow

interface PrivacyPolicyVM {

    val state : StateFlow<PrivacyPolicyContract.State>

    fun onEvent(intent: PrivacyPolicyContract.Intent)

}