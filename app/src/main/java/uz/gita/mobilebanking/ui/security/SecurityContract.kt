package uz.gita.mobilebanking.ui.security

class SecurityContract {

    data class State(
        val isError: Boolean = false,
        val fingerprintState: Boolean = false
    )

    sealed class Intent {
        object Backspace : Intent()
        object Fingerprint : Intent()
        object LoginWithPassword : Intent()
        object Success : Intent()
        data class Filled(val code: String) : Intent()
    }

    enum class SideEffect {
        FINGERPRINT
    }

}