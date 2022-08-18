package uz.gita.mobilebanking.ui.content

import androidx.compose.ui.graphics.vector.ImageVector

class ContentContract {

    data class State(
        val currentScreen: Screens = Screens.MAIN,
    )

    data class BottomData(
        val id: Int,
        val icon: ImageVector,
        val title: Int,
        val screen: Screens
    )

    enum class Screens {
        MAIN, TRANSFERS, PAYMENT, REPORTS, PROFILE
    }

    sealed class Intent {

    }

}