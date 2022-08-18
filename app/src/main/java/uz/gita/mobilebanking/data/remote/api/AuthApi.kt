package uz.gita.mobilebanking.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.mobilebanking.data.remote.request.AuthRequest
import uz.gita.mobilebanking.data.remote.response.AuthResponse
import uz.gita.mobilebankingMBF.BuildConfig.*

interface AuthApi {

    @POST(SIGN_IN)
    suspend fun signIn(@Body request: AuthRequest.SignInRequest): Response<AuthResponse.SignInResponse>

    @POST(SIGN_IN_VERIFY)
    suspend fun signInVerify(
        @Body request: AuthRequest.VerificationRequest
    ): Response<AuthResponse.VerificationResponse>

    @POST(SIGN_UP)
    suspend fun signUp(@Body request: AuthRequest.SignUpRequest): Response<AuthResponse.SignUpResponse>

    @POST(SIGN_UP_VERIFY)
    suspend fun signUpVerify(
        @Body request: AuthRequest.VerificationRequest
    ): Response<AuthResponse.VerificationResponse>

}