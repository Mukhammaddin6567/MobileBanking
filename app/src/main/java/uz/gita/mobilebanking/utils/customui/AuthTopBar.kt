package uz.gita.mobilebanking.utils.customui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebankingMBF.R

@Composable
fun AuthTopBar(
    modifier: Modifier = Modifier,
    isBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    title: Int
) {
    when (isBackButton) {
        false -> {
            Title(modifier = modifier, title = title, isBackButton = isBackButton)
        }
        else -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(
                    start = MaterialTheme.spacing.spacing_16dp,
                    end = MaterialTheme.spacing.spacing_16dp,
                    top = MaterialTheme.spacing.spacing_16dp
                )
            ) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false), // You can also change the color and radius of the ripple
                            onClick = { onBackClick.invoke() }
                        )
                )
                Title(title = title, modifier = Modifier.weight(1f), isBackButton = isBackButton)
                Image(
                    painterResource(id = R.drawable.ic_empty),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
private fun Title(title: Int, modifier: Modifier = Modifier, isBackButton: Boolean) {
    Text(
        modifier = modifier
            .padding(
                top = when (isBackButton) {
                    true -> {
                        0.dp
                    }
                    else -> {
                        MaterialTheme.spacing.spacing_16dp
                    }
                }
            )
            .fillMaxWidth(),
        text = stringResource(id = title),
        style = MaterialTheme.typography.h6.copy(
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
    )
}