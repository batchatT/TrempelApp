package com.example.trempelapp.presentation_layer.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.presentation_layer.mainScreen.home_page_fragments.HomePageFragmentViewModel

class HomePageViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageFragmentViewModel::class.java)) {
            return HomePageFragmentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
