package uz.gita.mobilebanking.ui.content.deprecated

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import uz.gita.mobilebankingMBF.R

sealed class BottomBarScreen(
    val route: String,
    val title: Int,
    val icon: ImageVector
) {

    object Main : BottomBarScreen(
        route = "main",
        title = R.string.main,
        icon = Icons.Default.Home
    )

    object Transfers : BottomBarScreen(
        route = "transfers",
        title = R.string.transfers,
        icon = Icons.Default.Translate
    )

    object Payment : BottomBarScreen(
        route = "payment",
        title = R.string.payment,
        icon = Icons.Default.Payment
    )

    object Reports : BottomBarScreen(
        route = "reports",
        title = R.string.reports,
        icon = Icons.Default.Report
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = R.string.profile,
        icon = Icons.Default.People
    )

}
