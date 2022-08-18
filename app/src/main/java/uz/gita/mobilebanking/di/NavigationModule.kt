package uz.gita.mobilebanking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.mobilebanking.navigation.AppNavigation
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

@Module
@InstallIn(ViewModelComponent::class)
class NavigationModule {

    @Provides
    fun provideSplashDirection(appNavigation: AppNavigation): SplashDirection =
        appNavigation.splashDirection

    @Provides
    fun provideSecurityScreen(appNavigation: AppNavigation): SecurityDirection =
        appNavigation.securityScreen

    @Provides
    fun provideLanguageDirection(appNavigation: AppNavigation): LanguageDirection =
        appNavigation.languageDirection

    @Provides
    fun provideOnboardDirection(appNavigation: AppNavigation): OnboardDirection =
        appNavigation.onboardDirection

    @Provides
    fun providePrivacyPolicyDirection(appNavigation: AppNavigation): PrivacyPolicyDirection =
        appNavigation.privacyPolicyDirection

    @Provides
    fun provideSignInDirection(appNavigation: AppNavigation): SignInDirection =
        appNavigation.signInScreenDirection

    @Provides
    fun provideSignUpDirection(appNavigation: AppNavigation): SignUpDirection =
        appNavigation.signUpScreenDirection

    @Provides
    fun provideVerifyDirection(appNavigation: AppNavigation): VerifyDirection =
        appNavigation.verifyDirection

    @Provides
    fun providePinCodeDirection(appNavigation: AppNavigation): PinCodeDirection =
        appNavigation.pinCodeDirection

    @Provides
    fun provideFingerprintDirection(appNavigation: AppNavigation): FingerprintDirection =
        appNavigation.fingerprintDirection

}