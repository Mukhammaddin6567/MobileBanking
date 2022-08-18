package uz.gita.mobilebanking.ui.verify

import uz.gita.mobilebankingMBF.R

class VerifyContract {

    data class State(
        val isProgress: Boolean = false,
        val phoneNumber: String = "",
        val timer: Long = 60000L,
        val isError: Boolean = false,
        val errorText: Int = R.string.invalid_sms_code,
        val dialogState: Boolean = false,
        val dialogText: Int = 0
    )

    sealed class Intent {
        object Back : Intent()
        object Dialog : Intent()
        object BackSpace : Intent()
        data class Verify(val code: String) : Intent()
    }

}