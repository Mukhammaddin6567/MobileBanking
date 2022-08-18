package uz.gita.mobilebanking.ui.privacy

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.Parcelize
import uz.gita.mobilebanking.ui.privacy.components.AppIcon
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebanking.utils.customui.AuthButton
import uz.gita.mobilebanking.utils.customui.AuthTopBar
import uz.gita.mobilebankingMBF.R

@Parcelize
class PrivacyPolicyScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "PrivacyPolicyScreen") {

    @Composable
    override fun Content() {
        val viewModel: PrivacyPolicyVM = viewModel<PrivacyPolicyVMImpl>()
        PrivacyPolicyContent(viewModel.state.collectAsState(), viewModel::onEvent)
    }

}

@Composable
private fun PrivacyPolicyContent(
    state: State<PrivacyPolicyContract.State>,
    event: (PrivacyPolicyContract.Intent) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        AuthTopBar(title = R.string.privacy_policy)

        AppIcon(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_16dp,
                    top = MaterialTheme.spacing.spacing_48dp
                )
        )

        Text(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_16dp,
                    top = MaterialTheme.spacing.spacing_16dp
                ),
            text = stringResource(id = R.string.privacy_policy_service),
            style = MaterialTheme.typography.body1.copy(
                fontWeight = W600
            )
        )

        Text(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_16dp,
                    end = MaterialTheme.spacing.spacing_16dp,
                    top = 8.dp
                ),
            text = stringResource(id = R.string.privacy_policy_text),
            style = MaterialTheme.typography.body2,
        )

        Row(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_4dp,
                    top = MaterialTheme.spacing.spacing_16dp,
                    bottom = 26.dp
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = state.value.buttonAcceptStatus,
                onClick = { event(PrivacyPolicyContract.Intent.CHECK) },
            )
            Text(
                text = stringResource(id = R.string.privacy_policy_agreement),
                style = MaterialTheme.typography.body2.copy(
                    when (state.value.buttonAcceptStatus) {
                        false -> MaterialTheme.colors.secondaryVariant
                        else -> MaterialTheme.colors.secondary
                    }
                )
            )
        }

        AuthButton(
            onClick = { event(PrivacyPolicyContract.Intent.ACCEPT) },
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_16dp,
                    end = MaterialTheme.spacing.spacing_16dp,
                    bottom = 32.dp
                ),
            enabled = state.value.buttonAcceptStatus,
            text = R.string.accept
        )

    }
}


@SuppressLint("UnrememberedMutableState")
@[Preview Composable]
private fun PrivacyPolicyPreview() {
    MobileBankingTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            PrivacyPolicyContent(mutableStateOf(PrivacyPolicyContract.State())) {}
        }
    }
}