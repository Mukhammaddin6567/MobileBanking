package uz.gita.mobilebanking.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.gita.mobilebanking.data.local.model.auth.AuthData
import uz.gita.mobilebanking.data.local.model.auth.asResource
import uz.gita.mobilebanking.data.local.model.auth.onResource
import uz.gita.mobilebanking.data.local.model.auth.onSuccess
import uz.gita.mobilebanking.domain.usecase.signup.SignUpUC
import uz.gita.mobilebanking.utils.Events.isConnection
import uz.gita.mobilebankingMBF.R
import javax.inject.Inject

@HiltViewModel
class SignUpVMImpl @Inject constructor(
    private val useCase: SignUpUC,
    private val direction: SignUpDirection
) : ViewModel(), SignUpVM {

    private var _state = MutableStateFlow(SignUpContract.State())
    override val state: StateFlow<SignUpContract.State> = _state.asStateFlow()

    override fun onEvent(intent: SignUpContract.Intent) {
        when (intent) {
            is SignUpContract.Intent.SignUp -> {
                Timber.d("onEvent isConnection: $isConnection")
                if (!isConnection) {
                    reduce { it.copy(isError = true, errorText = R.string.no_inet_connection) }
                    return
                }
                reduce { it.copy(isProgress = true, signUpButtonState = false) }
                val data = AuthData.SignUpData(
                    firstname = intent.firstname,
                    lastname = intent.lastname,
                    password = intent.password,
                    phoneNumber = intent.phoneNumber
                )
                useCase
                    .signUp(data = data)
                    .onEach { resultData ->
                        Timber.d("onEvent resultData: $resultData")
                        reduce { it.copy(isProgress = false, signUpButtonState = true) }
                        resultData.onSuccess {
                            viewModelScope.launch {
                                Timber.d("isTrusted: ${intent.isTrusted}")
                                useCase
                                    .setTrustedState(intent.isTrusted)
                                    .collectLatest {
                                        direction.navigateToVerifyScreen(intent.phoneNumber)
                                        setDefaultState()
                                    }
                            }
                        }
                        resultData.onResource {
                            val text = resultData.asResource.resourceId
                            reduce { it.copy(isError = true, errorText = text) }
                        }
                        /*resultData.onText {
                            reduce { it.copy(isError = true, errorText = R.string.user_available) }
                        }*/
                    }
                    .launchIn(viewModelScope)
            }
            is SignUpContract.Intent.Back -> {
                direction.popBackStack()
                setDefaultState()
            }
            is SignUpContract.Intent.Dialog -> {
                reduce { it.copy(isError = false) }
            }
        }
    }

    private fun reduce(content: (old: SignUpContract.State) -> SignUpContract.State) {
        val oldState = _state.value
        val newState = content(oldState)
        _state.value = newState
    }

    private fun setDefaultState() {
        _state.value = SignUpContract.State()
    }
}