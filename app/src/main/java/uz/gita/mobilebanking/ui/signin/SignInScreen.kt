package uz.gita.mobilebanking.ui.signin

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
class SignInScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "SignInScreen") {

    @Composable
    override fun Content() {
        val viewModel: SignInVM = viewModel<SignInVMImpl>()
        SignInContent(viewModel.state.collectAsState(), viewModel::onEvent)
    }

}

@Composable
private fun SignInContent(
    state: State<SignInContract.State>,
    event: (SignInContract.Intent) -> Unit,
) {
    val context = LocalContext.current
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    var onBackPressedTime by remember { mutableStateOf(0L) }
    BackHandler {
        if (onBackPressedTime + state.value.backPressedDuration > System.currentTimeMillis()) {
            (context as Activity).finish()
        } else {
            event(SignInContract.Intent.PopBackStack)
        }
        onBackPressedTime = System.currentTimeMillis()
    }
    if (state.value.isError) AppAlertDialog(
        buttonOnClick = { event(SignInContract.Intent.Dialog) },
        dialogText = state.value.errorText
    )
    if (state.value.isSnack) {
        val text = stringResource(id = state.value.snackText)
        LaunchedEffect(Unit) {
            val job = launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = text,
                    actionLabel = null,
                    duration = SnackbarDuration.Indefinite
                )
            }
            delay(state.value.backPressedDuration)
            job.cancel()
            event(SignInContract.Intent.SnackDismissed)
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface,
        topBar = { AuthTopBar(title = R.string.sign_in) },
        /*snackbarHost = { snackBarHostState ->

        }*/
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.spacing_16dp,
                    end = MaterialTheme.spacing.spacing_16dp
                )
        ) {
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
            val signInButtonState by remember { mutableStateOf(true) }
            val focusManager = LocalFocusManager.current

            Spacer(modifier = Modifier.weight(1f))

            AuthTextField(
                modifier = Modifier,
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

            Box(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_16dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            if (phoneNumberText.isEmpty() || phoneNumberText.length <= 12) {
                                phoneNumberEmptyError = true
                                phoneNumberErrorText = R.string.phone_is_empty
                                return@clickable
                            }
                            if (!phoneNumberState) {
                                phoneNumberError = true
                                phoneNumberErrorText = R.string.phone_is_wrong
                                return@clickable
                            }
                            event(SignInContract.Intent.ForgotPassword(phoneNumberText))
                        },
                    text = stringResource(id = R.string.forgot_password),
                    style = MaterialTheme.typography.body1.copy(
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colors.secondary
                    )
                )
            }

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

            if (state.value.isProgress) Box(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_16dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AuthCircularProgress()
            }

            AuthButton(
                modifier = Modifier.padding(top = MaterialTheme.spacing.spacing_32dp),
                text = R.string.sign_in,
                enabled = signInButtonState,
                onClick = {
                    if (state.value.isProgress) return@AuthButton
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
                        SignInContract.Intent.SignIn(
                            phoneNumber = phoneNumberText,
                            password = passwordText,
                            isTrusted = checkBoxState
                        )
                    )
                }
            )

            Row(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_16dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier.padding(end = MaterialTheme.spacing.spacing_8dp),
                    text = stringResource(id = R.string.sign_up_title),
                )

                Text(
                    modifier = Modifier
                        .clickable { event(SignInContract.Intent.SignUp) },
                    text = stringResource(id = R.string.sign_up),
                    style = MaterialTheme.typography.body1.copy(
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.secondary,
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@[Preview Composable]
private fun SignInPreview() {
    MobileBankingTheme {
        SignInContent(state = mutableStateOf(SignInContract.State()), event = {})
    }
}