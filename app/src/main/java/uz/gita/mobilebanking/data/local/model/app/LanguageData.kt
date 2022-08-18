package uz.gita.mobilebanking.data.local.model.app

data class LanguageData(
    val id: Int,
    val icon: String,
    val language: Int,
    val code: String,
    var isChecked: Boolean = false
)
