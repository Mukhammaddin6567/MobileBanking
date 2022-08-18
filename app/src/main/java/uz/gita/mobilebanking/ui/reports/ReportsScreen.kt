package uz.gita.mobilebanking.ui.reports

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import kotlinx.parcelize.Parcelize
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme

@Parcelize
class ReportsScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "ReportsScreen") {

    @Composable
    override fun Content() {
        ReportsContent()
    }

}

@Composable
private fun ReportsContent() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "Reports Screen", style = MaterialTheme.typography.h6)
        }
    }
}

@[Preview Composable]
private fun ReportsPreview() {
    MobileBankingTheme {
        ReportsContent()
    }
}