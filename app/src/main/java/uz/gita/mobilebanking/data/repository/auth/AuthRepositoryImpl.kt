package uz.gita.mobilebanking.data.repository.auth

import timber.log.Timber
import uz.gita.mobilebanking.data.local.AppSharedPreferences
import uz.gita.mobilebanking.data.local.model.auth.AuthData
import uz.gita.mobilebanking.data.local.model.auth.MessageData
import uz.gita.mobilebanking.data.local.model.auth.ResultData
import uz.gita.mobilebanking.data.remote.api.AuthApi
import uz.gita.mobilebanking.data.repository.Mapper.toRequest
import uz.gita.mobilebankingMBF.R
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val preferences: AppSharedPreferences
) : AuthRepository {

    override suspend fun signIn(data: AuthData.SignInData): ResultData<Unit> {
        val request = data.toRequest()
        val response = api.signIn(request)
        Timber.d("sign in response: $response")
        return when (response.code()) {
            200 -> {
                Timber.d("sign in: 200")
                val token = response.body()!!.token
                preferences.tempToken = token
                ResultData.Success(Unit)
            }
            403 -> {
                Timber.d("sign in: 403")
                ResultData.Fail(message = MessageData.Resource(R.string.user_not_found))
            }
            406 -> {
                Timber.d("sign in: 406")
                ResultData.Fail(message = MessageData.Resource(R.string.invalid_phone_number))
            }
            else -> {
                Timber.d("sign in: else")
                ResultData.Fail(message = MessageData.Resource(R.string.unknown_error))
            }
        }
    }

    override suspend fun signInVerify(data: AuthData.VerifyData): ResultData<Boolean> {
        val request = data.toRequest()
        val response =
            api.signInVerify(request = request)
        Timber.d("sign in verify response: $response")
        return when (response.code()) {
            200 -> {
                Timber.d("sign in verify: 200")
                val tokens = response.body()!!
                preferences.tempToken = ""
                preferences.accessToken = tokens.accessToken
                preferences.refreshToken = tokens.refreshToken
                ResultData.Success(preferences.isTrusted)
            }
            403 -> {
                Timber.d("sign in verify: 403")
                ResultData.Fail(message = MessageData.Resource(R.string.invalid_sms_code))
            }
            else -> {
                Timber.d("sign in verify: else")
                ResultData.Fail(message = MessageData.Resource(R.string.unknown_error))
            }
        }
    }

    override suspend fun signUp(data: AuthData.SignUpData): ResultData<Unit> {
        val request = data.toRequest()
        val response = api.signUp(request)
        Timber.d("sign up response: $response")
        return when (response.code()) {
            200 -> {
                Timber.d("sign up: 200")
                val token = response.body()!!.token
                Timber.d("token: $token")
                preferences.tempToken = token
                ResultData.Success(Unit)
            }
            403 -> {
                Timber.d("sign up: 403")
                ResultData.Fail(message = MessageData.Resource(R.string.user_available))
            }
            406 -> {
                Timber.d("sign up: 406")
                ResultData.Fail(message = MessageData.Resource(R.string.invalid_phone_number))
            }
            else -> {
                Timber.d("sign up: else")
                ResultData.Fail(message = MessageData.Resource(R.string.unknown_error))
            }
        }
    }

    override suspend fun signUpVerify(data: AuthData.VerifyData): ResultData<Boolean> {
        val request = data.toRequest()
        val response =
            api.signUpVerify(request = request)
        Timber.d("sign up verify response: $response")
        return when (response.code()) {
            200 -> {
                Timber.d("sign up verify: 200")
                val tokens = response.body()!!
                preferences.tempToken = ""
                preferences.accessToken = tokens.accessToken
                preferences.refreshToken = tokens.refreshToken
                ResultData.Success(preferences.isTrusted)
            }
            403 -> {
                Timber.d("sign up verify: 403")
                ResultData.Fail(message = MessageData.Resource(R.string.invalid_sms_code))
            }
            else -> {
                Timber.d("sign up verify: else")
                ResultData.Fail(message = MessageData.Resource(R.string.unknown_error))
            }
        }
    }

}