package uz.gita.mobilebanking.ui.signin

interface SignInDirection {

    fun navigateToVerifyScreen(phoneNumber: String)
    fun navigateToForgotPasswordScreen()
    fun navigateToSignUpScreen()

}