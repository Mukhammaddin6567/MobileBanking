package uz.gita.mobilebanking.data.remote.request

sealed class AuthRequest{

    data class SignInRequest(
        val password: String,
        val phone: String
    )

    data class SignUpRequest(
        val firstName: String,
        val lastName: String,
        val password: String,
        val phone: String
    )

    data class VerificationRequest(
        val code: String
    )

}
