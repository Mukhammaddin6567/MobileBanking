package uz.gita.mobilebanking.ui.signup

interface SignUpDirection {

    fun navigateToVerifyScreen(phoneNumber:String)
    fun popBackStack()

}