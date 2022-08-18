package uz.gita.mobilebanking.ui.signin

import kotlinx.coroutines.flow.StateFlow

interface SignInVM {

    val state: StateFlow<SignInContract.State>
    fun onEvent(intent: SignInContract.Intent)

}