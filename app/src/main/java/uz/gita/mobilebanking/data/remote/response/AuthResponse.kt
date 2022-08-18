package uz.gita.mobilebanking.data.remote.response

sealed class AuthResponse{

    data class SignInResponse(
        val token: String
    )

    data class SignUpResponse(
        val token: String
    )

    data class VerificationResponse(
        val accessToken: String,
        val refreshToken: String
    )

}
