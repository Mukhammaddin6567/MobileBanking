package uz.gita.mobilebanking.ui.splash

import kotlinx.coroutines.flow.StateFlow

interface SplashVM {

    val state: StateFlow<SplashContract.State>

}