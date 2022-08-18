package uz.gita.mobilebanking.data.repository.auth

import uz.gita.mobilebanking.data.local.model.auth.*

interface AuthRepository {

    suspend fun signIn(data: AuthData.SignInData): ResultData<Unit>
    suspend fun signInVerify(data: AuthData.VerifyData): ResultData<Boolean>

    suspend fun signUp(data: AuthData.SignUpData): ResultData<Unit>
    suspend fun signUpVerify(data: AuthData.VerifyData): ResultData<Boolean>

}