package com.example.trempelapp.presentation_layer.main_screen.main_fragments.home

import com.example.trempelapp.BaseViewModel
import com.example.utils.SingleLiveEvent
import javax.inject.Inject

class HomePageViewModel @Inject constructor() : BaseViewModel() {

    val moveToCategoriesLiveData: SingleLiveEvent<Boolean>
        get() = _moveToCategoriesLiveData
    private val _moveToCategoriesLiveData = SingleLiveEvent<Boolean>()

    fun onCategoriesButtonClick() {
        _moveToCategoriesLiveData.value = true
    }
}
