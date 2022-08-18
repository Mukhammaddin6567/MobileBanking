package uz.gita.mobilebanking.utils.customui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.font.FontWeight.Companion.W800
import androidx.compose.ui.tooling.preview.Preview
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import java.lang.StrictMath.sin
import kotlin.math.*

@Composable
fun CreditCard(
    name: String,
    amount: String,
    pan: String,
    expiredAt: String,

    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = MaterialTheme.shapes.large,
                color = Color.Cyan
            )
            .gradientBackground(
                colors = listOf(
                    Color.Black.copy(0.75f),
                    Color.Black.copy(0.65f),
                    Color.Black.copy(0.50f),
                    Color.Black.copy(0.35f),
                    Color.Black.copy(alpha = 0.45f),
                    Color.Black.copy(alpha = 0.55f),
                ), angle = 30f
            )
    ) {
        Text(
            modifier = Modifier.padding(
                top = MaterialTheme.spacing.spacing_16dp,
                start = MaterialTheme.spacing.spacing_16dp
            ),
            text = "Hamkorbank",
            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface)
        )

        Text(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.spacing_16dp,
                top = MaterialTheme.spacing.spacing_64dp
            ),
            text = "20 000.85 sum",
            style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onSurface)
        )

        Row(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_16dp,
                    end = MaterialTheme.spacing.spacing_16dp,
                    top = MaterialTheme.spacing.spacing_24dp,
                    bottom = MaterialTheme.spacing.spacing_16dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                text = "9860 16** **** 5397",
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = W600,
                    color = MaterialTheme.colors.onSurface
                )
            )

            Text(
                modifier = Modifier.padding(),
                text = "11/25",
                style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface)
            )

            Text(
                text = "UZCARD",
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onSurface,
                    fontStyle = FontStyle.Italic,
                    fontWeight = W800
                )
            )
        }
    }
}

private fun Modifier.gradientBackground(colors: List<Color>, angle: Float) = this.then(
    Modifier.drawBehind {
        val angleRad = angle / 180f * PI
        val x = cos(angleRad).toFloat() //Fractional x
        val y = sin(angleRad).toFloat() //Fractional y

        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
)

@[Preview Composable]
private fun CreditCardPreview() {
    MobileBankingTheme {
        CreditCard(
            name = "Hamkorbank",
            amount = "20 000.85 sum",
            pan = "9860 16** **** 5397",
            expiredAt = "11/25"
        )
    }
}