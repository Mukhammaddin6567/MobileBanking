package uz.gita.mobilebanking.ui.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.Parcelize
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebankingMBF.R

@Parcelize
class TestScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "TestScreen") {

    @Composable
    override fun Content() {
        TestScreenContent()
    }

}

@Composable
private fun TestScreenContent() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
        var value1 by remember { mutableStateOf("+998") }
        var value2 by remember { mutableStateOf("") }
        val hint1 by remember { mutableStateOf(R.string.phone_number) }
        val hint2 by remember { mutableStateOf(R.string.phone_number) }
        Column {
            TextField(
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.spacing.spacing_8dp,
                        end = MaterialTheme.spacing.spacing_8dp
                    )
                    .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = MaterialTheme.colors.onSurface,
                    cursorColor = MaterialTheme.colors.secondary,
                    unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    focusedLabelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                ),

                value = value1,
                onValueChange = {
                    value1 = it
                },
                label = { Text(text = stringResource(id = hint1)) },
                placeholder = { Text(text = "My placeholder1") }
            )
            TextField(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_16dp)
                    .fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = MaterialTheme.colors.onSurface,
                    cursorColor = MaterialTheme.colors.secondary,
                    unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                ),
                value = value2,
                onValueChange = {
                    value2 = it
                },
                label = { Text(text = stringResource(id = hint2)) },
                placeholder = { Text(text = "My placeholder1") }
            )
        }

    }

}

@[Preview Composable]
private fun TestScreenContentPreview() {
    MobileBankingTheme {
        TestScreenContent()
    }
}