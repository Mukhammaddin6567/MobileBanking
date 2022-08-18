package uz.gita.mobilebanking.domain.usecase.fingerprint

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.mobilebanking.data.repository.app.AppRepository
import javax.inject.Inject

class FingerprintUCImpl @Inject constructor(
    private val repository: AppRepository
) : FingerprintUC {

    override fun setFingerPrintState(state: Boolean) = flow<Unit> {
        emit(repository.saveFingerPrintState(state))
    }.flowOn(Dispatchers.IO)

}