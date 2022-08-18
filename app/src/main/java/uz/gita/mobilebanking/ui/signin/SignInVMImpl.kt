package uz.gita.mobilebanking.ui.signin

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
import uz.gita.mobilebanking.domain.usecase.signin.SignInUC
import uz.gita.mobilebanking.utils.Events
import uz.gita.mobilebankingMBF.R
import javax.inject.Inject

@HiltViewModel
class SignInVMImpl @Inject constructor(
    private val useCase: SignInUC,
    private val direction: SignInDirection
) : ViewModel(), SignInVM {

    private var _state = MutableStateFlow(SignInContract.State())
    override val state: StateFlow<SignInContract.State> = _state.asStateFlow()

    override fun onEvent(intent: SignInContract.Intent) {
        when (intent) {
            is SignInContract.Intent.SignIn -> {
                Timber.d("onEvent isConnection: ${Events.isConnection}")
                if (!Events.isConnection) {
                    reduce { it.copy(isError = true, errorText = R.string.no_inet_connection) }
                    return
                }
                reduce { it.copy(isProgress = true, signInButtonState = false) }
                val data = AuthData.SignInData(
                    phone = intent.phoneNumber,
                    password = intent.password
                )
                useCase
                    .signIn(data = data)
                    .onEach { resultData ->
                        Timber.d("onEvent resultData: $resultData")
                        reduce { it.copy(isProgress = false, signInButtonState = true) }
                        resultData.onSuccess {
                            viewModelScope.launch {
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
                            val text = resultData.asResource.resourceId
                            reduce { it.copy(isError = true, errorText = text) }
                        }*/
                    }.launchIn(viewModelScope)
            }
            is SignInContract.Intent.Dialog -> reduce { it.copy(isError = false) }
            is SignInContract.Intent.ForgotPassword -> {
                direction.navigateToForgotPasswordScreen()
            }
            is SignInContract.Intent.SignUp -> {
                direction.navigateToSignUpScreen()
                setDefaultState()
            }
            is SignInContract.Intent.PopBackStack -> reduce { it.copy(isSnack = true) }
            is SignInContract.Intent.SnackDismissed -> reduce { it.copy(isSnack = false) }
        }
    }

    private fun reduce(content: (old: SignInContract.State) -> SignInContract.State) {
        val oldState = _state.value
        val newState = content(oldState)
        _state.value = newState
    }

    private fun setDefaultState() {
        _state.value = SignInContract.State()
    }
}