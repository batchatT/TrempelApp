package com.example.trempelapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.presentation_layer.ViewModelProviderFactory
import com.example.trempelapp.presentation_layer.logInScreen.TrempelLogInViewModel
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.CartPageViewModel
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.CategoriesPageViewModel
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.FavouritesPageViewModel
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.HomePageViewModel
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.ProfilePageViewModel
import com.example.trempelapp.presentation_layer.splash_screen.TrempelSplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    abstract fun homePageViewModel(viewModel: HomePageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesPageViewModel::class)
    abstract fun categoriesPageViewModel(viewModel: CategoriesPageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartPageViewModel::class)
    abstract fun cartPageViewModel(viewModel: CartPageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesPageViewModel::class)
    abstract fun favouritesPageViewModel(viewModel: FavouritesPageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfilePageViewModel::class)
    abstract fun profilePageViewModel(viewModel: ProfilePageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrempelLogInViewModel::class)
    abstract fun logInViewModel(viewModel: TrempelLogInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrempelSplashViewModel::class)
    abstract fun splashViewModel(viewModel: TrempelSplashViewModel): ViewModel
}
