package uz.gita.mobilebanking.domain.usecase.signup

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

class SignUpUCImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val appRepository: AppRepository
) : SignUpUC {

    override fun signUp(data: AuthData.SignUpData) = flow<ResultData<Unit>> {
        emit(authRepository.signUp(data))
    }
        .catch { ResultData.Fail(message = MessageData.Resource(R.string.unknown_error)) }
        .flowOn(Dispatchers.IO)

    override fun setTrustedState(state: Boolean) = flow<Unit> {
        emit(appRepository.setTrustedState(state))
    }.flowOn(Dispatchers.IO)

}