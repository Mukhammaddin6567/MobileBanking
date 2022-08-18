package uz.gita.mobilebanking.domain.usecase.signin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.mobilebanking.data.local.model.auth.AuthData
import uz.gita.mobilebanking.data.local.model.auth.MessageData
import uz.gita.mobilebanking.data.local.model.auth.ResultData
import uz.gita.mobilebanking.data.repository.app.AppRepository
import uz.gita.mobilebanking.data.repository.auth.AuthRepository
import uz.gita.mobilebankingMBF.R
import javax.inject.Inject

class SignInUCImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val appRepository: AppRepository
) : SignInUC {

    override fun signIn(data: AuthData.SignInData) = flow<ResultData<Unit>> {
        emit(authRepository.signIn(data))
    }
        .catch { emit(ResultData.Fail(message = MessageData.Resource(R.string.unknown_error))) }
        .flowOn(Dispatchers.IO)

    override fun setTrustedState(state: Boolean) = flow<Unit> {
        emit(appRepository.setTrustedState(state))
    }.flowOn(Dispatchers.IO)
}