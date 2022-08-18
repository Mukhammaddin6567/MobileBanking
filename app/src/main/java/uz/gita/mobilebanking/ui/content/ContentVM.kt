package uz.gita.mobilebanking.ui.content

import kotlinx.coroutines.flow.StateFlow

interface ContentVM {

    val state: StateFlow<ContentContract.State>
    fun onEvent(intent: ContentContract.Intent)

}