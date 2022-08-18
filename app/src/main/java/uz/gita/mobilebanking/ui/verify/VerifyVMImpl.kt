package uz.gita.mobilebanking.ui.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.gita.mobilebanking.data.local.model.auth.*
import uz.gita.mobilebanking.domain.usecase.verify.VerifyUC
import uz.gita.mobilebanking.utils.Events.isConnection
import uz.gita.mobilebankingMBF.R
import javax.inject.Inject

@HiltViewModel
class VerifyVMImpl @Inject constructor(
    private val useCase: VerifyUC,
    private val direction: VerifyDirection
) : ViewModel(), VerifyVM {

    private var _state = MutableStateFlow(VerifyContract.State())
    override val state: StateFlow<VerifyContract.State> = _state.asStateFlow()
    private var timer: Long = _state.value.timer

    init {
        initTimer()
    }

    override fun onEvent(intent: VerifyContract.Intent) {
        when (intent) {
            is VerifyContract.Intent.Verify -> {
                if (!isConnection) {
                    reduce { it.copy(dialogState = true, dialogText = R.string.no_inet_connection) }
                    return
                }
                reduce { it.copy(isProgress = true) }
                val data = AuthData.VerifyData(code = intent.code)
                useCase
                    .verify(data)
                    .onEach { resultData ->
                        delay(2000)
                        reduce { it.copy(isProgress = false) }
                        resultData.onSuccess {
                            val isTrusted = resultData.asSuccess.data
                            Timber.d("isTrusted: $isTrusted")
                            if (isTrusted) direction.navigateToPinCodeScreen()
                            else direction.navigateToContentScreen()
                        }
                        /*resultData.onText {

                            val text = resultData.asResource.resourceId
                            reduce { it.copy(isError = true, errorText = text) }
                        }*/
                        resultData.onResource {
                            val text = resultData.asResource.resourceId
                            if (text != R.string.unknown_error) reduce {
                                it.copy(
                                    isError = true,
                                    errorText = text
                                )
                            }
                            else reduce {
                                it.copy(
                                    dialogState = true,
                                    dialogText = text
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
            is VerifyContract.Intent.Dialog -> {
                setDefaultState()
                direction.popBackStack()
            }
            is VerifyContract.Intent.Back -> {
                setDefaultState()
                direction.popBackStack()
            }
            is VerifyContract.Intent.BackSpace -> {
                reduce { it.copy(isError = false) }
            }
        }
    }

    override fun init(phoneNumber: String) {
        reduce { it.copy(phoneNumber = phoneNumber) }
    }

    private fun initTimer() {
        viewModelScope.launch {
            while (timer != 0L) {
                reduce { it.copy(timer = timer) }
                timer -= 1000L
                delay(1000)
                if (timer == 0L) reduce {
                    it.copy(
                        timer = timer,
                        isProgress = false,
                        dialogState = true,
                        dialogText = R.string.time_over
                    )
                }
            }
        }
    }

    private fun reduce(content: (old: VerifyContract.State) -> VerifyContract.State) {
        val oldState = _state.value
        val newState = content(oldState)
        _state.value = newState
    }

    private fun setDefaultState() {
        _state.value = VerifyContract.State()
    }
}