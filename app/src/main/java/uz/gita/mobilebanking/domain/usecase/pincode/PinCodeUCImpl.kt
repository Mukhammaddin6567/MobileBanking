package uz.gita.mobilebanking.domain.usecase.pincode

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.mobilebanking.data.repository.app.AppRepository
import javax.inject.Inject

class PinCodeUCImpl @Inject constructor(
    private val repository: AppRepository
) : PinCodeUC {

    override fun savePinCode(code: String) = flow<Unit> {
        emit(repository.savePinCode(code))
    }.flowOn(Dispatchers.IO)

}