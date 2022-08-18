package uz.gita.mobilebanking.domain.usecase.privacypolicy

import kotlinx.coroutines.flow.Flow

interface PrivacyPolicyUC {

    fun dismissFirstLaunch(): Flow<Unit>

}