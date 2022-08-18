package uz.gita.mobilebanking.utils.customui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebankingMBF.R

@Composable
fun AuthButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: Int,
    enabled: Boolean,
) {
    Button(
        onClick = { onClick.invoke() },
        modifier = modifier
            .height(MaterialTheme.spacing.spacing_48dp)
            .fillMaxWidth(),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = when (enabled) {
                false -> MaterialTheme.colors.secondaryVariant
                else -> MaterialTheme.colors.secondary
            }
        )
    ) {
        Text(
            text = stringResource(id = text),
            color = when (enabled) {
                false -> colorResource(id = R.color.text_light_gray)
                else -> colorResource(id = R.color.text_white)
            },
            style = MaterialTheme.typography.button
        )
    }
}