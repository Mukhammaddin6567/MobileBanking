package uz.gita.mobilebanking.ui.verify

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.Parcelize
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebanking.utils.convertLongToTime
import uz.gita.mobilebanking.utils.customui.*
import uz.gita.mobilebankingMBF.R

@Parcelize
class VerifyScreen(override val screenKey: String = uniqueScreenKey, val phoneNumber: String) :
    ComposeScreen(id = "VerifyScreen") {

    @Composable
    override fun Content() {
        val viewModel: VerifyVM = viewModel<VerifyVMImpl>()
        viewModel.init(phoneNumber)
        VerifyContent(viewModel.state.collectAsState(), viewModel::onEvent)
    }

}

@Composable
private fun VerifyContent(
    state: State<VerifyContract.State>,
    event: (VerifyContract.Intent) -> Unit
) {
    BackHandler {
        event(VerifyContract.Intent.Back)
    }
    if (state.value.dialogState) AppAlertDialog(
        buttonOnClick = { event(VerifyContract.Intent.Dialog) },
        dialogText = state.value.dialogText
    )
    Scaffold(
        topBar = {
            AuthTopBar(
                isBackButton = true,
                onBackClick = { event(VerifyContract.Intent.Back) },
                title = R.string.verification
            )
        },
        contentColor = MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        var isVerify by remember { mutableStateOf(false) }
        val isEnabled by remember { mutableStateOf(false) }
        var codeText by remember { mutableStateOf("") }
        val length by remember { mutableStateOf(6) }
        Column(
            modifier = Modifier.padding(
                top = MaterialTheme.spacing.spacing_64dp
            )
        ) {

            if (state.value.phoneNumber.isNotEmpty()) Text(
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.spacing.spacing_16dp,
                        end = MaterialTheme.spacing.spacing_16dp
                    )
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center),
                text = stringResource(id = R.string.code_sent, state.value.phoneNumber)
            )

            Text(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.spacing_8dp,
                        start = MaterialTheme.spacing.spacing_16dp,
                        end = MaterialTheme.spacing.spacing_16dp
                    )
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center),
                text = convertLongToTime(state.value.timer)
            )

            AuthTextField(
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.spacing_32dp,
                    start = MaterialTheme.spacing.spacing_16dp,
                    end = MaterialTheme.spacing.spacing_16dp
                ),
                label = R.string.confirm_sms_code,
                value = codeText,
                onValueChange = {},
                keyboardOptions = KeyboardOptions(),
                isError = state.value.isError,
                errorText = state.value.errorText,
                isEnabled = isEnabled
            )

            Box(
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.spacing_16dp,
                    start = MaterialTheme.spacing.spacing_16dp,
                    end = MaterialTheme.spacing.spacing_16dp
                )
            ) {
                CustomKeyboard(
                    numberOnClick = { digit ->
                        if (codeText.length >= length) return@CustomKeyboard
                        codeText += digit
                        isVerify = codeText.length == length
                    },
                    backSpaceOnClick = {
                        if (codeText.isNotEmpty()) {
                            event(VerifyContract.Intent.BackSpace)
                            codeText =
                                codeText.substring(0, codeText.length - 1)
                            isVerify = false
                        }
                    })
            }

            if (state.value.isProgress) AuthCircularProgress(modifier = Modifier.weight(1f))
            else Spacer(modifier = Modifier.weight(1f))

            AuthDefaultButton(
                onClick = {
                    if (state.value.isProgress) return@AuthDefaultButton
                    event(VerifyContract.Intent.Verify(code = codeText))
                },
                text = R.string.verification,
                isEnabled = isVerify
            )

        }
    }
}

@SuppressLint("UnrememberedMutableState")
@[Preview Composable]
fun VerifyPreview() {
    MobileBankingTheme {
        VerifyContent(state = mutableStateOf(VerifyContract.State()), event = {})
    }
}
