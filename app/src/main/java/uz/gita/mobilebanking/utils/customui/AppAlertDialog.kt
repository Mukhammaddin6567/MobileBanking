package uz.gita.mobilebanking.utils.customui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.tooling.preview.Preview
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebankingMBF.R

@Composable
fun AppAlertDialog(
    buttonOnClick: () -> Unit,
    title: Int = R.string.error,
    dialogText: Int,
    buttonText: Int = R.string.ok
) {
    AlertDialog(
        onDismissRequest = { buttonOnClick.invoke() },
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.h6.copy(fontWeight = W600)
            )
        },
        text = {
            Text(
                text = stringResource(id = dialogText),
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onSurface,
                )
            )
        },
        buttons = {
            Box(
                modifier = Modifier
                    .padding(
                        bottom = MaterialTheme.spacing.spacing_16dp,
                        end = MaterialTheme.spacing.spacing_24dp
                    )
                    .fillMaxWidth(), contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    modifier = Modifier.clickable {
                        buttonOnClick.invoke()
                    },
                    text = stringResource(id = buttonText),
                    style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface, fontWeight = W600)
                )
            }
        }
    )
}

@[Preview Composable]
private fun AuthAlertDialogPreview() {
    MobileBankingTheme {
        AppAlertDialog(
            title = R.string.error,
            dialogText = R.string.no_inet_connection,
            buttonText = R.string.ok,
            buttonOnClick = {})
    }
}