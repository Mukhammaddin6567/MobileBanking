package uz.gita.mobilebanking.ui.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(
                            width = MaterialTheme.spacing.spacing_24dp,
                            height = MaterialTheme.spacing.spacing_8dp
                        )
                        .clip(CircleShape)
                        .background(
                            color = MaterialTheme.colors.secondary
                        )
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(size = MaterialTheme.spacing.spacing_8dp)
                        .clip(CircleShape)
                        .background(
                            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.75f)
                        )
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@[Preview Composable]
private fun DotsIndicatorPreview() {
    MobileBankingTheme {
        DotsIndicator(totalDots = 4, selectedIndex = 3)
    }
}