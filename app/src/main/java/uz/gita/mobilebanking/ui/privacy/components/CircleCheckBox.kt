package uz.gita.mobilebanking.ui.privacy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun CircleCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var isChecked by remember { mutableStateOf(checked) }
    Image(
        imageVector = when (isChecked) {
            false -> Icons.Filled.Circle.apply { contentColorFor(backgroundColor = MaterialTheme.colors.secondaryVariant) }
            else -> Icons.Filled.CheckCircle.apply { contentColorFor(backgroundColor = MaterialTheme.colors.secondary) }
        },
        contentDescription = "AppCheckBox",
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false), // You can also change the color and radius of the ripple
                onClick = {
                    isChecked = !isChecked
                    onCheckedChange.invoke(isChecked)
                }
            )
    )
}