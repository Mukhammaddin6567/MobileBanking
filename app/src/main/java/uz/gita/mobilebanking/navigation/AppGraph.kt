package uz.gita.mobilebanking.navigation

import uz.gita.mobilebanking.ui.content.ContentScreen
import uz.gita.mobilebanking.ui.fingerprint.FingerprintScreen
import uz.gita.mobilebanking.ui.language.LanguageScreen
import uz.gita.mobilebanking.ui.main.MainScreen
import uz.gita.mobilebanking.ui.onboarding.OnboardScreen
import uz.gita.mobilebanking.ui.pincode.PinCodeScreen
import uz.gita.mobilebanking.ui.privacy.PrivacyPolicyScreen
import uz.gita.mobilebanking.ui.security.SecurityScreen
import uz.gita.mobilebanking.ui.signin.SignInScreen
import uz.gita.mobilebanking.ui.signup.SignUpScreen
import uz.gita.mobilebanking.ui.splash.SplashScreen
import uz.gita.mobilebanking.ui.verify.VerifyScreen
import javax.inject.Singleton

@Singleton
class AppGraph {

    fun splashScreen() = SplashScreen()
    fun securityScreen() = SecurityScreen()
    fun languageScreen() = LanguageScreen()
    fun onboardScreen() = OnboardScreen()
    fun privacyPolicyScreen() = PrivacyPolicyScreen()
    fun signInScreen() = SignInScreen()
    fun signUpScreen() = SignUpScreen()
    fun verifyScreen(phoneNumber: String) = VerifyScreen(phoneNumber = phoneNumber)
    fun pinCodeScreen() = PinCodeScreen()
    fun fingerprintScreen() = FingerprintScreen()

    fun contentScreen() = ContentScreen()
    fun mainScreen() = MainScreen()

}