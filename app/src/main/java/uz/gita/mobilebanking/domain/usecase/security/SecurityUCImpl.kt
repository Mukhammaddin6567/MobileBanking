package uz.gita.mobilebanking.domain.usecase.security

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.mobilebanking.data.repository.app.AppRepository
import javax.inject.Inject

class SecurityUCImpl @Inject constructor(
    private val repository: AppRepository
) : SecurityUC {

    override fun checkPinCode(code: String) = flow<Boolean> {
        emit(repository.getPinCode() == code)
    }.flowOn(Dispatchers.IO)

    override fun getFingerPrintState() = flow<Boolean> {
        emit(repository.getFingerPrintState())
    }.flowOn(Dispatchers.IO)

}