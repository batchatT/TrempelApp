package com.example.trempelapp.presentation_layer.main_screen

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.trempelapp.BaseActivity
import com.example.trempelapp.R
import com.example.trempelapp.databinding.ActivityHomeScreenBinding
import com.example.trempelapp.utils.DEEP_LINK
import com.example.trempelapp.utils.setupWithNavController

class HomeScreenActivity : BaseActivity() {

    private var currentNavController: LiveData<NavController>? = null

    private val binding by lazy {
        ActivityHomeScreenBinding.inflate(layoutInflater)
    }

    override fun changeTitle(title: String) {
        binding.toolbar.title = title
    }

    override fun hideAppBar() {
        binding.appbar.visibility = View.GONE
    }

    override fun showAppBar() {
        binding.appbar.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpNavigation()

        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        intent.extras?.let { extra ->
            val deepLink = extra.get(DEEP_LINK) as String?
            deepLink?.toUri()?.let { uri ->
                findNavController(R.id.nav_host_fragment).navigate(uri)
            }
        }
    }

    private fun setUpNavigation() {
        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = listOf(
                R.navigation.home_page_navigation,
                R.navigation.categories_page_navigation,
                R.navigation.cart_page_navigation,
                R.navigation.favourites_page_navigation,
                R.navigation.profile_page_navigation
            ),
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            null
        )

        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.homePageFragment,
                R.id.categoriesPage,
                R.id.cartPageFragment,
                R.id.favouritesPageFragment,
                R.id.profilePageFragment,
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )

        controller.observe(this) {
            binding.toolbar.setupWithNavController(it, appBarConfiguration)
        }

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}
