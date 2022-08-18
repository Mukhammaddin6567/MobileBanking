package uz.gita.mobilebanking.ui.content

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ContentVMImpl @Inject constructor(

) : ViewModel(), ContentVM {

    private var _state = MutableStateFlow(ContentContract.State())
    override val state: StateFlow<ContentContract.State> = _state.asStateFlow()

    override fun onEvent(intent: ContentContract.Intent) {

    }
}