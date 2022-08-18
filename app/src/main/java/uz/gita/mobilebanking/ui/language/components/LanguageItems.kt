package uz.gita.mobilebanking.ui.language.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import timber.log.Timber
import uz.gita.mobilebanking.data.local.model.app.AppLanguages
import uz.gita.mobilebanking.data.local.model.app.LanguageData
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebankingMBF.R

@Composable
fun LanguageItems(
    modifier: Modifier,
    languages: List<LanguageData>,
    selectedLanguage: (String) -> Unit
) {
    Column {
        repeat(languages.size) { position ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .indication(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false)
                    )
                    .clickable {
                        languages.map { it.isChecked = false }
                        languages[position].isChecked = true
                        selectedLanguage.invoke(languages[position].code)
                        Timber.d("selected: ${languages.map { it.isChecked }}")
                    }) {

                Text(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.spacing.spacing_16dp,
                            end = MaterialTheme.spacing.spacing_8dp
                        ),
                    text = languages[position].icon,
                    style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface)
                )

                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = languages[position].language),
                    style = MaterialTheme.typography.h6.copy(
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.W400
                    )
                )

                RadioButton(selected = languages[position].isChecked, onClick = {
                    languages.map { it.isChecked = false }
                    languages[position].isChecked = true
                    selectedLanguage.invoke(languages[position].code)
                    Timber.d("selected: ${languages.map { it.isChecked }}")
                })
            }
            if (position != languages.size - 1) {
                Divider(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.spacing.spacing_16dp,
                            end = MaterialTheme.spacing.spacing_16dp,
                            top = 4.dp,
                            bottom = 4.dp
                        )
                        .height(0.5.dp)
                )
            }
        }
    }
}

@[Preview Composable]
private fun AppRadioButtonPreview() {
    MobileBankingTheme {
        LanguageItems(
            modifier = Modifier,
            languages = listOf(
                LanguageData(
                    id = 1,
                    icon = "\uD83C\uDDEC\uD83C\uDDE7",
                    language = R.string.uzbek,
                    code = AppLanguages.UZ.value,
                    isChecked = true
                ), LanguageData(
                    id = 2,
                    icon = "\uD83C\uDDF7\uD83C\uDDFA",
                    language = R.string.russian,
                    code = AppLanguages.RU.value
                )
            ),
            selectedLanguage = {}
        )
    }
}