package uz.gita.mobilebanking.ui.content

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.Parcelize
import uz.gita.mobilebanking.ui.main.MainScreen
import uz.gita.mobilebanking.ui.payment.PaymentScreen
import uz.gita.mobilebanking.ui.profile.ProfileScreen
import uz.gita.mobilebanking.ui.reports.ReportsScreen
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.transfers.TransfersScreen
import uz.gita.mobilebankingMBF.R

@Parcelize
class ContentScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "ContentScreen") {

    @Composable
    override fun Content() {
        val viewModel: ContentVM = viewModel<ContentVMImpl>()
        ContentScreenContent(state = viewModel.state.collectAsState(), event = viewModel::onEvent)
    }

}

@Composable
private fun ContentScreenContent(
    state: State<ContentContract.State>,
    event: (ContentContract.Intent) -> Unit
) {
    val currentScreen by remember { mutableStateOf(ContentContract.Screens.MAIN) }
    val pagesLest by remember {
        mutableStateOf(
            listOf(
                ContentContract.BottomData(
                    id = 1,
                    icon = Icons.Outlined.Home,
                    title = R.string.main,
                    screen = ContentContract.Screens.MAIN
                ),
                ContentContract.BottomData(
                    id = 2,
                    icon = Icons.Default.TrackChanges,
                    title = R.string.transfers,
                    screen = ContentContract.Screens.TRANSFERS
                ),
                ContentContract.BottomData(
                    id = 3,
                    icon = Icons.Default.Payment,
                    title = R.string.payment,
                    screen = ContentContract.Screens.PAYMENT
                ),
                ContentContract.BottomData(
                    id = 3,
                    icon = Icons.Default.History,
                    title = R.string.reports,
                    screen = ContentContract.Screens.REPORTS
                ),
                ContentContract.BottomData(
                    id = 3,
                    icon = Icons.Outlined.Person,
                    title = R.string.profile,
                    screen = ContentContract.Screens.PROFILE
                ),
            )
        )
    }
    Column {
        when (state.value.currentScreen) {
            ContentContract.Screens.MAIN -> {
                MainScreen()
            }
            ContentContract.Screens.TRANSFERS -> {
                TransfersScreen()
            }
            ContentContract.Screens.PAYMENT -> {
                PaymentScreen()
            }
            ContentContract.Screens.REPORTS -> {
                ReportsScreen()
            }
            ContentContract.Screens.PROFILE -> {
                ProfileScreen()
            }
        }

        BottomView(
            currentScreen = currentScreen,
            pagesList = pagesLest
        )
    }
}

@Composable
private fun BottomView(
    currentScreen: ContentContract.Screens,
    pagesList: List<ContentContract.BottomData>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(pagesList.size) { position ->
            BottomItem(icon = pagesList[position].icon, title = pagesList[position].title)
        }
    }

}

@Composable
private fun BottomItem(icon: ImageVector, @StringRes title: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = "")
        Text(text = stringResource(id = title))
    }
}

@SuppressLint("UnrememberedMutableState")
@[Preview Composable]
private fun ContentScreenPreview() {
    MobileBankingTheme {
        ContentScreenContent(state = mutableStateOf(ContentContract.State()), event = {})
    }
}