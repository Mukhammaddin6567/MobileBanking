package uz.gita.mobilebanking.ui.security

import kotlinx.coroutines.flow.StateFlow

interface SecurityVM {

    val state: StateFlow<SecurityContract.State>
    fun onEvent(intent: SecurityContract.Intent)
    fun sideEffect(sideEffect: (SecurityContract.SideEffect) -> Unit)

}