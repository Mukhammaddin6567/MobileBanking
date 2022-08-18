package uz.gita.mobilebanking.utils.customui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import uz.gita.mobilebankingMBF.R

@Composable
fun AuthOutlinedTextField(
    modifier: Modifier,
    caption: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: Int = 0
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = caption),
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(start = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
            /*.height(56.dp)*/,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            isError = isError,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.text_field_background),
                focusedIndicatorColor = colorResource(id = R.color.text_field_focused_indicator),
                unfocusedIndicatorColor = colorResource(id = R.color.text_field_unfocused_indicator),
            ),
            shape = RoundedCornerShape(12.dp),
            textStyle = MaterialTheme.typography.subtitle1
        )

        if (isError) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = errorText),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.text_red)
                )
            )
        }
    }
}