package uz.gita.mobilebanking.domain.usecase.security

import kotlinx.coroutines.flow.Flow

interface SecurityUC {

    fun checkPinCode(code: String): Flow<Boolean>
    fun getFingerPrintState(): Flow<Boolean>

}