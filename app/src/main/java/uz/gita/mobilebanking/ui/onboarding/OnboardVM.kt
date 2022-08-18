package uz.gita.mobilebanking.ui.onboarding

interface OnboardVM {

    fun onEvent(intent: OnboardContract.Intent)
    fun sideEffect(sideEffect: (OnboardContract.SideEffect) -> Unit)

}