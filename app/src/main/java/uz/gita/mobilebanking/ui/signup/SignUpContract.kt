package uz.gita.mobilebanking.ui.signup

class SignUpContract {

    data class State(
        val isProgress: Boolean = false,
        val signUpButtonState: Boolean = true,
        val isError: Boolean = false,
        val errorText: Int = 0
    )

    sealed class Intent {
        object Dialog : Intent()
        object Back : Intent()
        data class SignUp(
            val firstname: String,
            val lastname: String,
            val phoneNumber: String,
            val password: String,
            val isTrusted: Boolean
        ) : Intent()
    }

}