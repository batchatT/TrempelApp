package com.example.trempelapp.di

import android.content.Context
import com.example.trempelapp.presentation_layer.logInScreen.TrempelLoginFragment
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.CartPageFragment
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.FavouritesPageFragment
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.HomePageFragment
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.ProfilePageFragment
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.category.CategoriesPageFragment
import com.example.trempelapp.presentation_layer.mainScreen.main_fragments.product.ProductListFragment
import com.example.trempelapp.presentation_layer.splash_screen.TrempelSplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        RetrofitModule::class,
        ProductModule::class,
        ViewModelModule::class
    ]
)
interface TrempelAppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TrempelAppComponent
    }

    fun inject(trempelSplashActivity: TrempelSplashActivity)

    fun inject(trempelLogInFragment: TrempelLoginFragment)
    fun inject(homePageFragment: HomePageFragment)
    fun inject(categoriesPageFragment: CategoriesPageFragment)
    fun inject(cartPageFragment: CartPageFragment)
    fun inject(favouritesPageFragment: FavouritesPageFragment)
    fun inject(profilePageFragment: ProfilePageFragment)
    fun inject(productListFragment: ProductListFragment)
}
