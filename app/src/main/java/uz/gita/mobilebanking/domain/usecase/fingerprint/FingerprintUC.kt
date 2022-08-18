package uz.gita.mobilebanking.domain.usecase.fingerprint

import kotlinx.coroutines.flow.Flow

interface FingerprintUC {

    fun setFingerPrintState(state: Boolean): Flow<Unit>

}