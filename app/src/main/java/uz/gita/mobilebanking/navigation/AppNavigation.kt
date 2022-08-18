package uz.gita.mobilebanking.navigation

import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.back
import com.github.terrakok.modo.forward
import com.github.terrakok.modo.replace
import uz.gita.mobilebanking.ui.fingerprint.FingerprintDirection
import uz.gita.mobilebanking.ui.language.LanguageDirection
import uz.gita.mobilebanking.ui.onboarding.OnboardDirection
import uz.gita.mobilebanking.ui.pincode.PinCodeDirection
import uz.gita.mobilebanking.ui.privacy.PrivacyPolicyDirection
import uz.gita.mobilebanking.ui.security.SecurityDirection
import uz.gita.mobilebanking.ui.signin.SignInDirection
import uz.gita.mobilebanking.ui.signup.SignUpDirection
import uz.gita.mobilebanking.ui.splash.SplashDirection
import uz.gita.mobilebanking.ui.verify.VerifyDirection
import javax.inject.Inject

class AppNavigation @Inject constructor(
    private val modo: Modo,
    private val graph: AppGraph
) {

    val splashDirection = object : SplashDirection {
        override fun navigateToSecurityScreen() {
            modo.replace(graph.securityScreen())
        }

        override fun navigateToContentScreen() {
            modo.replace(graph.contentScreen())
        }

        override fun navigateToSignInScreen() {
            modo.replace(graph.signInScreen())
        }

        override fun navigateToLanguageScreen() {
            modo.replace(graph.languageScreen())
        }
    }

    val securityScreen = object : SecurityDirection {
        override fun navigateToContentScreen() {
            modo.replace(graph.contentScreen())
        }

        override fun navigateToSignInScreen() {
            modo.replace(graph.signInScreen())
        }
    }

    val languageDirection = object : LanguageDirection {
        override fun navigateToOnBoardingScreen() {
            modo.replace(graph.onboardScreen())
        }
    }

    val onboardDirection = object : OnboardDirection {
        override fun popBackStack() {
            modo.back()
        }

        override fun navigateToPrivacyPolicyScreen() {
            modo.replace(graph.privacyPolicyScreen())
        }
    }

    val privacyPolicyDirection = object : PrivacyPolicyDirection {
        override fun navigateToSignInScreen() {
            modo.replace(graph.signInScreen())
        }
    }

    val signInScreenDirection = object : SignInDirection {
        override fun navigateToVerifyScreen(phoneNumber: String) {
            modo.forward(graph.verifyScreen(phoneNumber))
        }

        override fun navigateToForgotPasswordScreen() {
        }

        override fun navigateToSignUpScreen() {
            modo.forward(graph.signUpScreen())
        }
    }

    val signUpScreenDirection = object : SignUpDirection {
        override fun navigateToVerifyScreen(phoneNumber: String) {
            modo.forward(graph.verifyScreen(phoneNumber))
        }

        override fun popBackStack() {
            modo.back()
        }

    }

    val verifyDirection = object : VerifyDirection {
        override fun navigateToContentScreen() {
            modo.replace(graph.contentScreen())
        }

        override fun navigateToPinCodeScreen() {
            modo.replace(graph.pinCodeScreen())
        }

        override fun popBackStack() {
            modo.back()
        }
    }

    val pinCodeDirection = object : PinCodeDirection {
        override fun navigateToSignUpScreen() {
            modo.replace(graph.signUpScreen())
        }

        override fun navigateToFingerprintScreen() {
            modo.replace(graph.fingerprintScreen())
        }

        override fun navigateToContentScreen() {
            modo.replace(graph.contentScreen())
        }
    }

    val fingerprintDirection = object : FingerprintDirection {
        override fun navigateToSignUpScreen() {
            modo.replace(graph.signUpScreen())
        }

        override fun navigateToContentScreen() {
            modo.replace(graph.contentScreen())
        }
    }

}