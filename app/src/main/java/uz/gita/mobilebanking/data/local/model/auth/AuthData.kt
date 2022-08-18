package uz.gita.mobilebanking.data.local.model.auth

sealed class AuthData{

    data class SignInData(
        val phone: String,
        val password: String
    )

    data class SignUpData(
        val firstname: String,
        val lastname: String,
        val password: String,
        val phoneNumber: String
    )

    data class VerifyData(
        val code: String
    )

    data class VerificationData(
        val code: String
    )


}
