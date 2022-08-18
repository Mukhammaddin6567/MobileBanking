package uz.gita.mobilebanking.ui.pincode

class PinCodeContract {

    sealed class Intent {
        object Back : Intent()
        object Skip : Intent()
        data class Submit(val code: String) : Intent()
    }

}