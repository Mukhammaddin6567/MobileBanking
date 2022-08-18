package uz.gita.mobilebanking.ui.pincode

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.mobilebanking.domain.usecase.pincode.PinCodeUC
import javax.inject.Inject

@HiltViewModel
class PinCodeVMImpl @Inject constructor(
    private val useCase: PinCodeUC,
    private val direction: PinCodeDirection
) : ViewModel(), PinCodeVM {

    override fun onEvent(intent: PinCodeContract.Intent) {
        when (intent) {
            is PinCodeContract.Intent.Skip -> direction.navigateToContentScreen()
            is PinCodeContract.Intent.Submit -> {
                viewModelScope.launch {
                    useCase
                        .savePinCode(intent.code)
                        .collectLatest {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) direction.navigateToFingerprintScreen()
                            else direction.navigateToContentScreen()
                        }
                }
            }
            is PinCodeContract.Intent.Back -> direction.navigateToSignUpScreen()
        }
    }
}