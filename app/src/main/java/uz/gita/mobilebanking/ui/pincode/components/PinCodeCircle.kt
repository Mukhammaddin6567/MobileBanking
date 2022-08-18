package uz.gita.mobilebanking.ui.pincode.components

import androidx.compose.animation.core.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebankingMBF.R

@Composable
fun PinCodeCircle(
    modifier: Modifier,
    currentLength: Int,
    maxLength: Int
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(currentLength) {
            Image(
                painter = painterResource(id = R.drawable.ic_pin_checked),
                contentDescription = ""
            )
        }
        repeat(maxLength - currentLength) {
            Image(
                painter = painterResource(id = R.drawable.ic_pin_unchecked),
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
fun PinCodePreview() {
    MobileBankingTheme {
        PinCodeCircle(modifier = Modifier, currentLength = 5, maxLength = 15)
    }
}