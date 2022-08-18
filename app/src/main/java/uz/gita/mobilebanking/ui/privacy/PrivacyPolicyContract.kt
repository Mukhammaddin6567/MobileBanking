package uz.gita.mobilebanking.ui.privacy

class PrivacyPolicyContract {

    data class State(val buttonAcceptStatus: Boolean = false)

    enum class Intent{
        CHECK,
        ACCEPT
    }

}