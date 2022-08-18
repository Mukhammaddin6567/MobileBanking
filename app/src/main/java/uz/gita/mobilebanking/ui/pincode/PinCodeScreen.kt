package uz.gita.mobilebanking.ui.pincode

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.Parcelize
import uz.gita.mobilebanking.ui.pincode.components.PinCodeTextFields
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebanking.utils.customui.CustomKeyboard
import uz.gita.mobilebanking.utils.customui.TextHint
import uz.gita.mobilebankingMBF.R

@Parcelize
class PinCodeScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "PinCodeScreen") {

    @Composable
    override fun Content() {
        val viewModel: PinCodeVM = viewModel<PinCodeVMImpl>()
        PinCodeContent(viewModel::onEvent)
    }

}

@Composable
private fun PinCodeContent(
    event: (PinCodeContract.Intent) -> Unit
) {
    BackHandler {
        event(PinCodeContract.Intent.Back)
    }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
        Column {
            val length by remember { mutableStateOf(4) }
            var codeText by remember { mutableStateOf("") }
            val isCheckButton by remember { mutableStateOf(true) }
            var checkButtonState by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.spacing_16dp,
                        end = MaterialTheme.spacing.spacing_16dp
                    )
                    .fillMaxWidth(), contentAlignment = Alignment.CenterEnd
            ) {
                TextHint(
                    text = R.string.skip,
                    onClick = { event(PinCodeContract.Intent.Skip) }
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_32dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.setup_pin_code),
                style = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center)
            )

            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_16dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.fast_enter_pin_code),
                style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.weight(1f))

            PinCodeTextFields(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spacing_24dp),
                length = length,
                code = codeText,
                isFilled = { checkButtonState = it })

            Box(
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.spacing_64dp,
                    bottom = MaterialTheme.spacing.spacing_32dp
                )
            ) {
                CustomKeyboard(
                    numberOnClick = { digit ->
                        if (codeText.length < length) codeText += digit
                    },
                    backSpaceOnClick = {
                        if (codeText.isNotEmpty()) {
                            codeText = codeText.substring(0, codeText.length - 1)
                        }
                    },
                    isCheckButton = isCheckButton,
                    checkButtonState = checkButtonState,
                    checkOnClick = { event(PinCodeContract.Intent.Submit(code = codeText)) }
                )
            }
        }
    }
}

@[Preview Composable]
private fun PinCodePreview() {
    MobileBankingTheme {
        PinCodeContent() {}
    }
}