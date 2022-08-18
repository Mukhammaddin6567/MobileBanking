package uz.gita.mobilebanking.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val spacing_0dp: Dp = 0.dp,
    val spacing_4dp: Dp = 4.dp,
    val spacing_8dp: Dp = 8.dp,
    val spacing_16dp: Dp = 16.dp,
    val spacing_24dp: Dp = 24.dp,
    val spacing_32dp: Dp = 32.dp,
    val spacing_48dp: Dp = 48.dp,
    val spacing_64dp: Dp = 64.dp,
    val spacing_72dp: Dp = 72.dp,
    val spacing_80dp: Dp = 80.dp,
    val spacing_88dp: Dp = 88.dp,
    val spacing_96dp: Dp = 96.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current