package uz.gita.mobilebanking.domain.usecase.pincode

import kotlinx.coroutines.flow.Flow

interface PinCodeUC {

    fun savePinCode(code: String): Flow<Unit>

}