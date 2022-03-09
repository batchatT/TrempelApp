package com.example.trempelapp.presentation_layer.main_screen.main_fragments.profile

import androidx.lifecycle.viewModelScope
import com.example.domain_layer.coroutine_use_cases.LogOutUseCaseImpl
import com.example.domain_layer.coroutine_use_cases.execute
import com.example.trempelapp.BaseViewModel
import com.example.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfilePageViewModel @Inject constructor(
    private val logOut: LogOutUseCaseImpl
) : BaseViewModel() {

    val onLogOutButtonClicked: SingleLiveEvent<Void>
        get() = _onLogOutButtonClicked
    private val _onLogOutButtonClicked = SingleLiveEvent<Void>()

    fun onLogOutClick() {
        viewModelScope.launch {
            logOut.execute()
        }
        _onLogOutButtonClicked.call()
    }
}
