package uz.gita.mobilebanking.ui.fingerprint

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebanking.utils.customui.AuthButton
import uz.gita.mobilebanking.utils.customui.TextHint
import uz.gita.mobilebankingMBF.R

@Parcelize
class FingerprintScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "FingerprintScreen") {

    @IgnoredOnParcel
    private var cancellationSignal: CancellationSignal? = null

    @RequiresApi(Build.VERSION_CODES.P)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: FingerprintVM = viewModel<FingerprintVMImpl>()
        FingerprintContent(viewModel::onEvent)

        viewModel.sideEffect { sideEffect ->
            when (sideEffect) {
                FingerprintContract.SideEffect.LAUNCH -> {
                    launchBiometric(context, viewModel::onEvent)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun authenticationCallback(event: (FingerprintContract.Intent) -> Unit): BiometricPrompt.AuthenticationCallback =
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Timber.d("authenticationCallback: onAuthenticationSucceeded")
                event(FingerprintContract.Intent.SUCCESS)
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

    /*@IgnoredOnParcel
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback =
        @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Timber.d("authenticationCallback: onAuthenticationSucceeded")
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
        }*/

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
    private fun launchBiometric(context: Context, event: (FingerprintContract.Intent) -> Unit) {
        if (checkBiometricSupport(context)) {
            val biometricPrompt = BiometricPrompt.Builder(context)
                .apply {
                    setTitle(context.getString(R.string.fingerprint_auth))
//                    setSubtitle(context.getString(R.string.put_your_finger))
                    setDescription(context.getString(R.string.put_your_finger))
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
private fun FingerprintContent(
    event: (FingerprintContract.Intent) -> Unit
) {
    BackHandler {
        event(FingerprintContract.Intent.BACK)
    }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
        Column {
            val turnOnState by remember { mutableStateOf(true) }
            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_48dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.fingerprint),
                style = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center)
            )

            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.spacing_16dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.fast_enter_fingerprint),
                style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center)
            )

            Spacer(modifier = Modifier.weight(1f))



            Icon(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.spacing_96dp)
                    .aspectRatio(1f),
                imageVector = Icons.Default.Fingerprint,
                tint = MaterialTheme.colors.onSurface.copy(0.4f),
                contentDescription = ""
            )

            Spacer(modifier = Modifier.weight(1f))

            AuthButton(
                onClick = { event(FingerprintContract.Intent.TURN_ON) },
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spacing_16dp),
                text = R.string.turn_on,
                enabled = turnOnState
            )
            Box(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.spacing_24dp,
                        bottom = MaterialTheme.spacing.spacing_32dp
                    )
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                TextHint(
                    text = R.string.add_later,
                    onClick = { event(FingerprintContract.Intent.SKIP) }
                )
            }

        }
    }
}

@[Preview Composable]
private fun FingerprintPreview() {
    MobileBankingTheme {
        FingerprintContent { }
    }
}