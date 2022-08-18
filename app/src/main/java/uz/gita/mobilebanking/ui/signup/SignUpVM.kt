package uz.gita.mobilebanking.ui.signup

import kotlinx.coroutines.flow.StateFlow

interface SignUpVM {

    val state: StateFlow<SignUpContract.State>
    fun onEvent(intent: SignUpContract.Intent)

}