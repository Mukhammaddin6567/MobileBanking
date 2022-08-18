package uz.gita.mobilebanking.ui.language

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.Parcelize
import uz.gita.mobilebanking.data.local.model.app.LanguageData
import uz.gita.mobilebanking.ui.language.components.LanguageItems
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebanking.utils.SetLanguage
import uz.gita.mobilebanking.utils.customui.AuthDefaultButton
import uz.gita.mobilebanking.utils.customui.AuthTopBar

@Parcelize
class LanguageScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "LanguageScreen") {

    @Composable
    override fun Content() {
        val viewModel: LanguageVM = viewModel<LanguageVMImpl>()
        LanguageScreenContent(state = viewModel.state.collectAsState(), event = viewModel::onEvent)
    }

}

@Composable
private fun LanguageScreenContent(
    state: State<LanguageContract.State>,
    event: (intent: LanguageContract.Intent) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface
    ) {
        var listOfLanguages by remember { mutableStateOf<List<LanguageData>>(emptyList()) }
        var selectedLanguage by remember { mutableStateOf(state.value.currentLanguage) }

        Column {
            AuthTopBar(title = state.value.titleText)

            Spacer(modifier = Modifier.weight(1f))

            if (state.value.languages.isNotEmpty()) {
                listOfLanguages = state.value.languages

                SetLanguage(language = state.value.currentLanguage)

                LanguageItems(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.spacing.spacing_8dp,
                            end = MaterialTheme.spacing.spacing_8dp
                        ),
                    languages = listOfLanguages,
                    selectedLanguage = { language ->
                        selectedLanguage = language
                        event(LanguageContract.Intent.Selected(selectedLanguage))
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            AuthDefaultButton(
                onClick = { event(LanguageContract.Intent.Next(selectedLanguage)) },
                modifier = Modifier,
                text = state.value.nextButtonText
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@[Preview Composable]
private fun LanguageScreenPreview() {
    MobileBankingTheme {
        LanguageScreenContent(state = mutableStateOf(LanguageContract.State()), event = {})
    }
}