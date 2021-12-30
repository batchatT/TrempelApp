package com.example.trempelapp.presentation_layer.mainScreen.home_page_fragments

import com.example.trempelapp.BaseViewModel
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.domain_layer.FindProductsUseCaseImpl
import javax.inject.Inject

class HomePageFragmentViewModel : BaseViewModel() {

    @Inject
    lateinit var findProducts: FindProductsUseCaseImpl

    override fun injectDagger(application: TrempelApplication) {
        application.trempelApp.inject(this)
    }
}
