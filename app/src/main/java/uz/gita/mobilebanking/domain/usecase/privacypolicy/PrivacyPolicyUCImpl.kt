package uz.gita.mobilebanking.domain.usecase.privacypolicy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.mobilebanking.data.repository.app.AppRepository
import javax.inject.Inject

class PrivacyPolicyUCImpl @Inject constructor(
    private val repository: AppRepository
) : PrivacyPolicyUC {

    override fun dismissFirstLaunch() = flow<Unit> {
        emit(repository.dismissFirstLaunch())
    }.flowOn(Dispatchers.IO)

}