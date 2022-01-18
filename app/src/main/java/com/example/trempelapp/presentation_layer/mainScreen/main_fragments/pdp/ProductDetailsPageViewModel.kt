package com.example.trempelapp.presentation_layer.mainScreen.main_fragments.pdp

import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.data_layer.models.Product
import javax.inject.Inject

class ProductDetailsPageViewModel @Inject constructor() : BaseViewModel() {

    lateinit var product: Product
}
