package uz.gita.mobilebanking.utils.customui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.gita.mobilebanking.ui.theme.spacing

@Composable
fun AuthDefaultButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: Int,
    isEnabled: Boolean = true
) {
    Button(
        onClick = { onClick.invoke() },
        modifier = modifier
            .height(MaterialTheme.spacing.spacing_48dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        ),
        enabled = isEnabled
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.button
        )
    }
}