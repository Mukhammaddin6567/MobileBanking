package uz.gita.mobilebanking.ui.fingerprint

interface FingerprintVM {

    fun onEvent(intent: FingerprintContract.Intent)
    fun sideEffect(sideEffect: (FingerprintContract.SideEffect) -> Unit)

}