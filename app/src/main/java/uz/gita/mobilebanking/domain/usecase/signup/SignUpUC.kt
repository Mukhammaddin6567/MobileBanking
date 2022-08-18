package uz.gita.mobilebanking.domain.usecase.signup

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.local.model.auth.AuthData
import uz.gita.mobilebanking.data.local.model.auth.ResultData

interface SignUpUC {

     fun signUp(data: AuthData.SignUpData): Flow<ResultData<Unit>>
     fun setTrustedState(state: Boolean): Flow<Unit>

}