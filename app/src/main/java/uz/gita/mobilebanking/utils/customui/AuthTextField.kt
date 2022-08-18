package uz.gita.mobilebanking.utils.customui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import uz.gita.mobilebanking.ui.theme.spacing

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: Int = 0,
    isEnabled: Boolean = true,
) {
    Column(modifier = modifier) {
        TextField(
            label = { Text(text = stringResource(id = label)) },
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                /*.onFocusChanged { onFocusChanged.invoke(it) }*/
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                /*errorIndicatorColor = MaterialTheme.colors.error,*/
                disabledTextColor = MaterialTheme.colors.onSurface,
                trailingIconColor = MaterialTheme.colors.onSurface.copy(alpha = 0.75f),
                errorTrailingIconColor = MaterialTheme.colors.onSurface.copy(alpha = 0.75f),
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.onSurface,
                cursorColor = MaterialTheme.colors.secondary,
                unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                focusedLabelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                errorLabelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            singleLine = true,
            textStyle = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.W500),
            isError = isError,
            enabled = isEnabled
        )
        if (isError) Text(
            modifier = Modifier.padding(
                top = MaterialTheme.spacing.spacing_4dp,
                start = MaterialTheme.spacing.spacing_16dp
            ),
            text = stringResource(id = errorText),
            style = MaterialTheme.typography.body1.copy(
                fontSize = 12.sp,
                color = MaterialTheme.colors.error
            )
        )
    }
}