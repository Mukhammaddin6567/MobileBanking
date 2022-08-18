package uz.gita.mobilebanking.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Elevation(
    val elevation_0dp: Dp = 0.dp,
    val elevation_4dp: Dp = 4.dp,
    val elevation_8dp: Dp = 8.dp,
    val elevation_16dp: Dp = 16.dp,
    val elevation_24dp: Dp = 24.dp,
    val elevation_32dp: Dp = 32.dp,
    val elevation_64dp: Dp = 64.dp,
)

val LocalElevation = compositionLocalOf { Elevation() }

val MaterialTheme.elevation: Elevation
    @Composable
    @ReadOnlyComposable
    get() = LocalElevation.current