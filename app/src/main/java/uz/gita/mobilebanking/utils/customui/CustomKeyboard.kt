package uz.gita.mobilebanking.utils.customui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.tooling.preview.Preview
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing

@Composable
fun CustomKeyboard(
    numberOnClick: (digit: Int) -> Unit,
    backSpaceOnClick: () -> Unit,
    isFingerprint: Boolean = false,
    fingerprintOnClick: () -> Unit = {},
    isCheckButton: Boolean = false,
    checkButtonState: Boolean = false,
    checkOnClick: () -> Unit = {},
) {
    Column {
        Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.spacing_8dp))
        Row(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_8dp,
                    end = MaterialTheme.spacing.spacing_8dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberButton(onClick = numberOnClick, number = 1, modifier = Modifier.weight(1f))
            NumberButton(onClick = numberOnClick, number = 2, modifier = Modifier.weight(1f))
            NumberButton(onClick = numberOnClick, number = 3, modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_8dp,
                    end = MaterialTheme.spacing.spacing_8dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberButton(onClick = numberOnClick, number = 4, modifier = Modifier.weight(1f))
            NumberButton(onClick = numberOnClick, number = 5, modifier = Modifier.weight(1f))
            NumberButton(onClick = numberOnClick, number = 6, modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_8dp,
                    end = MaterialTheme.spacing.spacing_8dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NumberButton(onClick = numberOnClick, number = 7, modifier = Modifier.weight(1f))
            NumberButton(onClick = numberOnClick, number = 8, modifier = Modifier.weight(1f))
            NumberButton(onClick = numberOnClick, number = 9, modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_8dp,
                    end = MaterialTheme.spacing.spacing_8dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isFingerprint) {
                ActionButton(
                    onClick = fingerprintOnClick,
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Fingerprint
                )
                NumberButton(onClick = numberOnClick, number = 0, modifier = Modifier.weight(1f))
                ActionButton(
                    onClick = backSpaceOnClick,
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Backspace
                )
            } else if (isCheckButton) {
                ActionButton(
                    onClick = backSpaceOnClick,
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Backspace
                )
                NumberButton(onClick = numberOnClick, number = 0, modifier = Modifier.weight(1f))
                ActionButton(
                    onClick = checkOnClick,
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Check,
                    tint = when (checkButtonState) {
                        false -> MaterialTheme.colors.onSurface
                        else -> MaterialTheme.colors.secondary
                    }
                )
            } else {
                Box(modifier = Modifier.weight(1f))
                NumberButton(onClick = numberOnClick, number = 0, modifier = Modifier.weight(1f))
                ActionButton(
                    onClick = backSpaceOnClick,
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Backspace
                )
            }
        }
        Spacer(modifier = Modifier.padding(bottom = MaterialTheme.spacing.spacing_8dp))
    }
}

@Composable
private fun NumberButton(
    onClick: (digit: Int) -> Unit,
    number: Int,
    modifier: Modifier
) {
    TextButton(
        onClick = { onClick.invoke(number) },
        modifier = modifier
            .padding(MaterialTheme.spacing.spacing_8dp),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = "$number",
            style = MaterialTheme.typography.h6.copy(fontWeight = W600)
        )
    }
}

@Composable
private fun ActionButton(
    onClick: () -> Unit,
    modifier: Modifier,
    icon: ImageVector,
    tint: Color = MaterialTheme.colors.onSurface
) {
    TextButton(
        onClick = { onClick.invoke() },
        modifier = modifier
            .padding(MaterialTheme.spacing.spacing_8dp),
        shape = MaterialTheme.shapes.small
    ) {
        Icon(imageVector = icon, tint = tint, contentDescription = "")
    }
}

@Preview
@Composable
private fun OTPKeyboardPreview() {
    MobileBankingTheme {
        Surface {
            CustomKeyboard(
                numberOnClick = {},
                backSpaceOnClick = {}
            )
        }
    }
}
