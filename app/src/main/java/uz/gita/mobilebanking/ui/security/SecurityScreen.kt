package uz.gita.mobilebanking.ui.security

import android.Manifest
import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import timber.log.Timber
import uz.gita.mobilebanking.ui.pincode.components.PinCodeTextFields
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebanking.utils.customui.CustomKeyboard
import uz.gita.mobilebankingMBF.R

@Parcelize
class SecurityScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "SecurityScreen") {

    @IgnoredOnParcel
    private var cancellationSignal: CancellationSignal? = null

    @RequiresApi(Build.VERSION_CODES.P)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: SecurityVM = viewModel<SecurityVMImpl>()
        SecurityContent(state = viewModel.state.collectAsState(), event = viewModel::onEvent)
        viewModel.sideEffect { sideEffect ->
            when (sideEffect) {
                SecurityContract.SideEffect.FINGERPRINT -> {
                    launchBiometric(context, viewModel::onEvent)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun authenticationCallback(event: (SecurityContract.Intent) -> Unit): BiometricPrompt.AuthenticationCallback =
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Timber.d("authenticationCallback: onAuthenticationSucceeded")
                event(SecurityContract.Intent.Success)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                Timber.d("authenticationCallback: onAuthenticationError")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Timber.d("authenticationCallback: onAuthenticationFailed")
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpCode, helpString)
                Timber.d("authenticationCallback: onAuthenticationHelp")
            }
        }

    private fun checkBiometricSupport(context: Context): Boolean {
        val keyGuardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyGuardManager.isDeviceSecure) {
            return true
        }
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }

        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun launchBiometric(context: Context, event: (SecurityContract.Intent) -> Unit) {
        if (checkBiometricSupport(context)) {
            val biometricPrompt = BiometricPrompt.Builder(context)
                .apply {
                    setTitle(context.getString(R.string.fingerprint_auth))
                    setSubtitle(context.getString(R.string.auth_fingerprint))
//                    setDescription(context.getString(R.string.auth_fingerprint))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        setConfirmationRequired(true)
                    }
                    setNegativeButton(
                        context.getString(R.string.cancel),
                        context.mainExecutor
                    ) { _, _ ->
                    }
                }.build()

            biometricPrompt.authenticate(
                getCancellationSignal(),
                context.mainExecutor,
                authenticationCallback(event)
            )
        }
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Timber.tag("TTT").d("getCancellationSignal")
        }

        return cancellationSignal as CancellationSignal
    }

}

@Composable
private fun SecurityContent(
    state: State<SecurityContract.State>,
    event: (SecurityContract.Intent) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
        Column {
            val length by remember { mutableStateOf(4) }
            var codeText by remember { mutableStateOf("") }

            Spacer(modifier = Modifier.weight(1f))

            PinCodeTextFields(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spacing_24dp),
                length = length,
                code = codeText,
                isFilled = { state ->
                    if (state) event(SecurityContract.Intent.Filled(codeText))
                })

            Timber.d("isError: ${state.value.isError}")
            if (state.value.isError) Text(
                text = stringResource(id = R.string.pin_invalidate),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.spacing.spacing_32dp,
                        bottom = MaterialTheme.spacing.spacing_16dp
                    )
                    .fillMaxWidth(),
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center
                )
            )

            Box {
                CustomKeyboard(
                    numberOnClick = { digit ->
                        if (codeText.length < length) codeText += digit
                    },
                    backSpaceOnClick = {
                        if (codeText.isNotEmpty()) {
                            event(SecurityContract.Intent.Backspace)
                            codeText = codeText.substring(0, codeText.length - 1)
                        }
                    },
                    isFingerprint = state.value.fingerprintState,
                    fingerprintOnClick = { event(SecurityContract.Intent.Fingerprint) }
                )
            }

            Box(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.spacing_16dp,
                        bottom = MaterialTheme.spacing.spacing_80dp
                    )
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .clickable { event(SecurityContract.Intent.LoginWithPassword) },
                    text = stringResource(id = R.string.auth_by_password),
                    style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.secondary)
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@[Preview Composable]
private fun SecurityPreview() {
    MobileBankingTheme {
        SecurityContent(state = mutableStateOf(SecurityContract.State()), event = {})
    }
}