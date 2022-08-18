package uz.gita.mobilebanking.ui.content.deprecated

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.gita.mobilebanking.ui.main.MainScreen
import uz.gita.mobilebanking.ui.payment.PaymentScreen
import uz.gita.mobilebanking.ui.profile.ProfileScreen
import uz.gita.mobilebanking.ui.reports.ReportsScreen
import uz.gita.mobilebanking.ui.transfers.TransfersScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomBarScreen.Main.route) {
        composable(route = BottomBarScreen.Main.route) {
            MainScreen()
        }
        composable(route = BottomBarScreen.Transfers.route) {
            TransfersScreen()
        }
        composable(route = BottomBarScreen.Payment.route) {
            PaymentScreen()
        }
        composable(route = BottomBarScreen.Reports.route) {
            ReportsScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
    }
}