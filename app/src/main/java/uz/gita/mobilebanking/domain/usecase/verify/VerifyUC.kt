package uz.gita.mobilebanking.domain.usecase.verify

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.local.model.auth.AuthData
import uz.gita.mobilebanking.data.local.model.auth.ResultData

interface VerifyUC {

    fun verify(data: AuthData.VerifyData): Flow<ResultData<Boolean>>

}