package com.example.trempelapp.presentation_layer.mainScreen.main_fragments.home

import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.domain_layer.FindProductsUseCaseImpl
import com.example.trempelapp.utils.SingleLiveEvent
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val findProducts: FindProductsUseCaseImpl,
) : BaseViewModel() {

    val moveToCategoriesLiveData: SingleLiveEvent<Boolean>
        get() = _moveToCategoriesLiveData
    private val _moveToCategoriesLiveData = SingleLiveEvent<Boolean>()

    fun onCategoriesButtonClick() {
        _moveToCategoriesLiveData.value = true
    }
}
