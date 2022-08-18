package uz.gita.mobilebanking.ui.signin

import uz.gita.mobilebankingMBF.R

class SignInContract {

    data class State(
        val isProgress: Boolean = false,
        val isSnack: Boolean = false,
        val signInButtonState: Boolean = true,
        val isError: Boolean = false,
        val errorText: Int = 0,
        val snackText: Int = R.string.snackbar_exit,
        val backPressedDuration: Long = 2000L
    )

    sealed class Intent {
        object PopBackStack : Intent()
        object Dialog : Intent()
        object SignUp : Intent()
        object SnackDismissed : Intent()
        data class ForgotPassword(val phoneNumber: String) : Intent()
        data class SignIn(
            val phoneNumber: String,
            val password: String,
            val isTrusted: Boolean
        ) : Intent()
    }

}