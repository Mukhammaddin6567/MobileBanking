package uz.gita.mobilebanking.utils

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import java.util.*

@Composable
fun PasswordTrailingIcon(onIconClick: () -> Unit, passwordVisible: Boolean) {
    val image = if (passwordVisible) Icons.Filled.Visibility
    else Icons.Filled.VisibilityOff
    // Please provide localized description for accessibility services
    val description =
        if (passwordVisible) "Hide password" else "Show password"

    IconButton(onClick = { onIconClick.invoke() }) {
        Icon(imageVector = image, description)
    }
}

fun passwordVisualTransformation(passwordVisible: Boolean): VisualTransformation {
    return if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
}

@Composable
fun SetLanguage(language: String) {
    val locale = Locale(language)
    val configuration = LocalConfiguration.current
    configuration.setLocale(locale)
    val resources = LocalContext.current.resources
    resources.updateConfiguration(configuration, resources.displayMetrics)
}