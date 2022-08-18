package uz.gita.mobilebanking.domain.usecase.signin

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.local.model.auth.AuthData
import uz.gita.mobilebanking.data.local.model.auth.ResultData

interface SignInUC {

    fun signIn(data: AuthData.SignInData): Flow<ResultData<Unit>>
    fun setTrustedState(state: Boolean): Flow<Unit>

}