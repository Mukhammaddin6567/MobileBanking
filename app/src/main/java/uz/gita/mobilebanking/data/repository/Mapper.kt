package uz.gita.mobilebanking.data.repository

import uz.gita.mobilebanking.data.local.model.auth.AuthData
import uz.gita.mobilebanking.data.remote.request.AuthRequest

object Mapper {

    fun AuthData.SignUpData.toRequest() = AuthRequest.SignUpRequest(
        firstName = firstname,
        lastName = lastname,
        phone = phoneNumber,
        password = password
    )

    fun AuthData.SignInData.toRequest() = AuthRequest.SignInRequest(
        phone = phone,
        password = password
    )

    fun AuthData.VerifyData.toRequest() = AuthRequest.VerificationRequest(code = code)

}