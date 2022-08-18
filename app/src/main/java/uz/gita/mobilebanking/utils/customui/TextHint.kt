package uz.gita.mobilebanking.utils.customui

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebankingMBF.R

@Composable
fun TextHint(
    modifier: Modifier = Modifier,
    text: Int,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier.clickable { onClick.invoke() },
        text = stringResource(id = text),
        style = MaterialTheme.typography.button.copy(
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
        )
    )
}

@[Preview Composable]
private fun TextHintPreview() {
    MobileBankingTheme {
        TextHint(modifier = Modifier, text = R.string.text_bank, onClick = {})
    }
}