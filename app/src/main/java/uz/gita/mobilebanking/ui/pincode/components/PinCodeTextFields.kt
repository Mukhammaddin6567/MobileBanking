package uz.gita.mobilebanking.ui.pincode.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import timber.log.Timber
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing

@Composable
fun PinCodeTextFields(
    modifier: Modifier,
    length: Int,
    code: String,
    isFilled: (Boolean) -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        if (code.isEmpty()) {
            Timber.d("onFilled: empty")
            isFilled.invoke(false)
            repeat(length) {
                PinCodeBox(isFilled = false)
            }
        } else if (code.length < length) {
            Timber.d("onFilled: false")
            isFilled.invoke(false)
            repeat(code.length) {
                PinCodeBox(true)
            }
            repeat(length - code.length) {
                PinCodeBox(false)
            }
        } else {
            Timber.d("onFilled: true")
            isFilled.invoke(true)
            repeat(length) {
                PinCodeBox(true)
            }
        }
    }
}

@Composable
private fun PinCodeBox(isFilled: Boolean) {
    Box(
        modifier = Modifier
            .background(
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
            )
    ) {

        when (isFilled) {
            true -> {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(durationMillis = 10000))
                ) {
                    Image(
                        modifier = Modifier.padding(MaterialTheme.spacing.spacing_24dp),
                        imageVector = Icons.Default.Circle,
                        contentDescription = ""
                    )
                }

            }
            else -> {
                AnimatedVisibility(
                    visible = true,
                    exit = fadeOut(animationSpec = tween(durationMillis = 10000))
                ) {
                    Icon(
                        modifier = Modifier.padding(MaterialTheme.spacing.spacing_24dp),
                        imageVector = Icons.Default.ArrowBack,
                        tint = Color.Transparent,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OTPTextFieldsPreview() {
    MobileBankingTheme {
        Surface {
            PinCodeTextFields(
                length = 4,
                code = "1234",
                modifier = Modifier,
                isFilled = {}
            )
        }
    }
}
