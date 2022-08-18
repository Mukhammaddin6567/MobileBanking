package uz.gita.mobilebanking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.mobilebanking.domain.usecase.fingerprint.FingerprintUC
import uz.gita.mobilebanking.domain.usecase.fingerprint.FingerprintUCImpl
import uz.gita.mobilebanking.domain.usecase.language.LanguageUC
import uz.gita.mobilebanking.domain.usecase.language.LanguageUCImpl
import uz.gita.mobilebanking.domain.usecase.pincode.PinCodeUC
import uz.gita.mobilebanking.domain.usecase.pincode.PinCodeUCImpl
import uz.gita.mobilebanking.domain.usecase.privacypolicy.PrivacyPolicyUC
import uz.gita.mobilebanking.domain.usecase.privacypolicy.PrivacyPolicyUCImpl
import uz.gita.mobilebanking.domain.usecase.security.SecurityUC
import uz.gita.mobilebanking.domain.usecase.security.SecurityUCImpl
import uz.gita.mobilebanking.domain.usecase.signin.SignInUC
import uz.gita.mobilebanking.domain.usecase.signin.SignInUCImpl
import uz.gita.mobilebanking.domain.usecase.signup.SignUpUC
import uz.gita.mobilebanking.domain.usecase.signup.SignUpUCImpl
import uz.gita.mobilebanking.domain.usecase.splash.SplashUC
import uz.gita.mobilebanking.domain.usecase.splash.SplashUCImpl
import uz.gita.mobilebanking.domain.usecase.verify.VerifyUC
import uz.gita.mobilebanking.domain.usecase.verify.VerifyUCImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindSplashUC(impl: SplashUCImpl): SplashUC

    @Binds
    fun bindSecurityUC(impl: SecurityUCImpl): SecurityUC

    @Binds
    fun bindLanguageUC(impl: LanguageUCImpl): LanguageUC

    @Binds
    fun bindPrivacyPolicyUC(impl: PrivacyPolicyUCImpl): PrivacyPolicyUC

    @Binds
    fun bindSignInUC(impl: SignInUCImpl): SignInUC

    @Binds
    fun bindSignUpUC(impl: SignUpUCImpl): SignUpUC

    @Binds
    fun bindVerifyUC(impl: VerifyUCImpl): VerifyUC

    @Binds
    fun bindPinCodeUC(impl: PinCodeUCImpl): PinCodeUC

    @Binds
    fun bindFingerprintUC(impl: FingerprintUCImpl): FingerprintUC

}