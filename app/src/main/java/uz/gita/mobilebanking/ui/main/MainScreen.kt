package uz.gita.mobilebanking.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpCenter
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.CreditScore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.Parcelize
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebankingMBF.R

@Parcelize
class MainScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "MainScreen") {

    @Composable
    override fun Content() {
        MainContent()
    }

}

@Composable
private fun MainContent() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface,
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.spacing_16dp,
                    start = MaterialTheme.spacing.spacing_16dp,
                    end = MaterialTheme.spacing.spacing_16dp
                )
            ) {
                Icon(
                    modifier = Modifier
                        .clickable { },
                    imageVector = Icons.Outlined.HelpCenter,
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.main),
                    style = MaterialTheme.typography.h6.copy(
                        textAlign = TextAlign.Center
                    )
                )
                Icon(
                    modifier = Modifier
                        .clickable {},
                    imageVector = Icons.Rounded.CreditScore, contentDescription = ""
                )
            }
        }
    ) {
        val scrollableState = rememberScrollState()
        Column(
            modifier = Modifier
                .scrollable(state = scrollableState, orientation = Orientation.Vertical)
        ) {
            val state = true
            if (state) {
                Column(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.spacing_16dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.aspectRatio(4f),
                        imageVector = Icons.Rounded.CreditCard,
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(id = R.string.first_card),
                        style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center)
                    )
                }
            } else {

            }
        }
    }
}

@[Preview Composable]
private fun MainPreview() {
    MobileBankingTheme {
        MainContent()
    }
}