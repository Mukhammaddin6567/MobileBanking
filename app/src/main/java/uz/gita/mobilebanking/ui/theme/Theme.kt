package uz.gita.mobilebanking.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@SuppressLint("ConflictingOnColor")
private val Theme1_LightPalette = lightColors(
    primary = Primary_LT1,
    primaryVariant = PrimaryVariant_LT1,
    secondary = Secondary_LT1,
    background = Background_LT1,
    secondaryVariant = SecondaryVariant_LT1,
    surface = Surface_LT1,
    error = Error_LT1,
    onPrimary = OnPrimary_LT1,
    onSecondary = OnSecondary_LT1,
    onBackground = OnBackground_LT1,
    onSurface = OnSurface_LT1,
    onError = OnError_LT1
)

@SuppressLint("ConflictingOnColor")
private val Theme1_DarkPalette = darkColors(
    primary = Primary_DT1,
    primaryVariant = PrimaryVariant_DT1,
    secondary = Secondary_DT1,
    background = Background_DT1,
    secondaryVariant = SecondaryVariant_DT1,
    surface = Surface_DT1,
    error = Error_DT1,
    onPrimary = OnPrimary_DT1,
    onSecondary = OnSecondary_DT1,
    onBackground = OnBackground_DT1,
    onSurface = OnSurface_DT1,
    onError = OnError_DT1
)

@Composable
fun MobileBankingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        Theme1_DarkPalette
    } else {
        Theme1_LightPalette
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalElevation provides Elevation()
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

/*
@Composable
fun Theme1(
    darkTheme: Boolean = isSystemInDarkTheme()
)*/
