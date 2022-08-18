package uz.gita.mobilebanking.ui.verify

import kotlinx.coroutines.flow.StateFlow

interface VerifyVM {

    val state: StateFlow<VerifyContract.State>
    fun onEvent(intent: VerifyContract.Intent)
    fun init(phoneNumber:String)

}