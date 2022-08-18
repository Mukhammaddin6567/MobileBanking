package uz.gita.mobilebanking.ui.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.Parcelize
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.utils.SetLanguage
import uz.gita.mobilebankingMBF.R

@Parcelize
class SplashScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "SplashScreen") {
    @Composable
    override fun Content() {
        val viewModel: SplashVM = viewModel<SplashVMImpl>()
        SplashScreenContent(viewModel.state.collectAsState())
    }
}

@Composable
private fun SplashScreenContent(
    state: State<SplashContract.State>
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        color = MaterialTheme.colors.surface
    ) {
        if (state.value.language.isNotEmpty()) SetLanguage(language = state.value.language)

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_gita),
                contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.text_bank),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = W500,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colors.primary
                )
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun SplashScreenPreview() {
    MobileBankingTheme { SplashScreenContent(state = mutableStateOf(SplashContract.State())) }
}