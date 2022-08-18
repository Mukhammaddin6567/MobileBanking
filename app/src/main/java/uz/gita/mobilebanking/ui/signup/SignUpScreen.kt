package uz.gita.mobilebanking.ui.signup

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.Parcelize
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebanking.utils.PasswordTrailingIcon
import uz.gita.mobilebanking.utils.customui.*
import uz.gita.mobilebanking.utils.isPhoneNumber
import uz.gita.mobilebanking.utils.passwordVisualTransformation
import uz.gita.mobilebanking.utils.phoneNumberFilter
import uz.gita.mobilebankingMBF.R

@Parcelize
class SignUpScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "SignUpScreen") {

    @Composable
    override fun Content() {
        val viewModel: SignUpVM = viewModel<SignUpVMImpl>()
        SignUpContent(viewModel.state.collectAsState(), viewModel::onEvent)
    }

}

@Composable
private fun SignUpContent(
    state: State<SignUpContract.State>,
    event: (SignUpContract.Intent) -> Unit,
) {
    BackHandler {
        event(SignUpContract.Intent.Back)
    }

    if (state.value.isError) AppAlertDialog(
        buttonOnClick = { event(SignUpContract.Intent.Dialog) },
        dialogText = state.value.errorText
    )
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        topBar = {
            AuthTopBar(
                isBackButton = true,
                onBackClick = { event(SignUpContract.Intent.Back) },
                title = R.string.sign_up
            )
        }) {
        Column(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.spacing_16dp,
                end = MaterialTheme.spacing.spacing_16dp
            )
        ) {
            var firstnameText by remember { mutableStateOf("") }
            var firstnameEmptyError by remember { mutableStateOf(false) }
            var firstnameError by remember { mutableStateOf(false) }
            var firstnameErrorText by remember { mutableStateOf(0) }
            var lastnameText by remember { mutableStateOf("") }
            var lastnameError by remember { mutableStateOf(false) }
            var lastnameEmptyError by remember { mutableStateOf(false) }
            var lastnameErrorText by remember { mutableStateOf(0) }
            var phoneNumberText by remember { mutableStateOf("+998") }
            var phoneNumberState by remember { mutableStateOf(false) }
            var phoneNumberError by remember { mutableStateOf(false) }
            var phoneNumberEmptyError by remember { mutableStateOf(false) }
            var phoneNumberErrorText by remember { mutableStateOf(0) }
            var passwordText by remember { mutableStateOf("") }
            var passwordState by remember { mutableStateOf(false) }
            var passwordError by remember { mutableStateOf(false) }
            var passwordEmptyError by remember { mutableStateOf(false) }
            var passwordErrorText by remember { mutableStateOf(0) }
            var passwordTrailingIconState by remember { mutableStateOf(false) }
            var checkBoxState by remember { mutableStateOf(false) }
            val signUpButtonState by remember { mutableStateOf(true) }
            val focusManager = LocalFocusManager.current

            Spacer(modifier = Modifier.weight(1f))

            AuthTextField(
                label = R.string.firstname,
                value = firstnameText,
                onValueChange = { name ->
                    if (firstnameError) firstnameError = false
                    if (firstnameEmptyError) firstnameEmptyError = false
                    if (name.length >= 20) return@AuthTextField
                    firstnameText = name
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = firstnameEmptyError || firstnameError,
                errorText = firstnameErrorText
            )

            AuthTextField(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_16dp),
                label = R.string.lastname,
                value = lastnameText,
                onValueChange = { family ->
                    if (lastnameError) lastnameError = false
                    if (lastnameEmptyError) lastnameEmptyError = false
                    if (family.length >= 20) return@AuthTextField
                    lastnameText = family
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = lastnameEmptyError || lastnameError,
                errorText = lastnameErrorText
            )

            AuthTextField(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_16dp),
                label = R.string.phone_number,
                value = phoneNumberText,
                onValueChange = { phone ->
                    if (phone.length > 13) return@AuthTextField
                    if (phoneNumberError) phoneNumberError = false
                    if (phoneNumberEmptyError) phoneNumberEmptyError = false
                    phoneNumberText = phone
                    phoneNumberState = phoneNumberText.isPhoneNumber()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = { phoneNumberFilter(it) },
                isError = phoneNumberError || phoneNumberEmptyError,
                errorText = phoneNumberErrorText
            )

            AuthTextField(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_16dp),
                label = R.string.enter_password,
                value = passwordText,
                onValueChange = { password ->
                    if (password.length >= 20) return@AuthTextField
                    if (passwordError) passwordError = false
                    if (passwordEmptyError) passwordEmptyError = false
                    passwordText = password
                    passwordState = passwordText.length >= 6
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                visualTransformation = passwordVisualTransformation(passwordTrailingIconState),
                trailingIcon = {
                    PasswordTrailingIcon(onIconClick = {
                        passwordTrailingIconState = !passwordTrailingIconState
                    }, passwordVisible = passwordTrailingIconState)
                },
                isError = passwordError || passwordEmptyError,
                errorText = passwordErrorText
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.spacing_16dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Checkbox(
                    modifier = Modifier,
                    checked = checkBoxState,
                    onCheckedChange = { checkBoxState = !checkBoxState })

                Text(
                    text = stringResource(id = R.string.is_trusted_device)
                )
            }

            if (state.value.isProgress) AuthCircularProgress(modifier = Modifier.padding(top = MaterialTheme.spacing.spacing_32dp))

            AuthButton(
                modifier = Modifier.padding(top = MaterialTheme.spacing.spacing_32dp),
                text = R.string.sign_up,
                enabled = signUpButtonState,
                onClick = {
                    if (!state.value.signUpButtonState) return@AuthButton
                    if (firstnameText.isEmpty()) {
                        firstnameEmptyError = true
                        firstnameErrorText = R.string.firstname_is_empty
                        return@AuthButton
                    }
                    if (firstnameText.length < 3) {
                        firstnameError = true
                        firstnameErrorText = R.string.firstname_is_error
                        return@AuthButton
                    }
                    if (lastnameText.isEmpty()) {
                        lastnameEmptyError = true
                        lastnameErrorText = R.string.lastname_is_empty
                        return@AuthButton
                    }
                    if (lastnameText.length < 3) {
                        lastnameError = true
                        lastnameErrorText = R.string.lastname_is_error
                        return@AuthButton
                    }
                    if (phoneNumberText.isEmpty() || phoneNumberText.length <= 12) {
                        phoneNumberEmptyError = true
                        phoneNumberErrorText = R.string.phone_is_empty
                        return@AuthButton
                    }
                    if (!phoneNumberState) {
                        phoneNumberError = true
                        phoneNumberErrorText = R.string.phone_is_wrong
                        return@AuthButton
                    }
                    if (passwordText.isEmpty()) {
                        passwordEmptyError = true
                        passwordErrorText = R.string.password_is_empty
                        return@AuthButton
                    }
                    if (!passwordState) {
                        passwordError = true
                        passwordErrorText = R.string.password_is_wrong
                        return@AuthButton
                    }
                    focusManager.clearFocus()
                    event(
                        SignUpContract.Intent.SignUp(
                            firstname = firstnameText,
                            lastname = lastnameText,
                            phoneNumber = phoneNumberText,
                            password = passwordText,
                            isTrusted = checkBoxState
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.weight(1f))

        }

    }
}


@SuppressLint("UnrememberedMutableState")
@[Preview Composable]
private fun SignUpPreview() {
    MobileBankingTheme {
        SignUpContent(state = mutableStateOf(SignUpContract.State()), event = {})
    }
}