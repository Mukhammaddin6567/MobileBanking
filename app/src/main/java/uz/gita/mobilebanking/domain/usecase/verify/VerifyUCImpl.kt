package uz.gita.mobilebanking.domain.usecase.verify

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

class VerifyUCImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val appRepository: AppRepository
) : VerifyUC {

    override fun verify(data: AuthData.VerifyData) = flow<ResultData<Boolean>> {
        emit(authRepository.signUpVerify(data))
    }
        .catch { ResultData.Fail(message = MessageData.Resource(R.string.unknown_error)) }
        .flowOn(Dispatchers.IO)


}