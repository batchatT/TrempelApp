package com.example.trempelapp.presentation_layer.mainScreen

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.trempelapp.BaseActivity
import com.example.trempelapp.R
import com.example.trempelapp.databinding.ActivityHomeScreenBinding

class HomeScreenActivity : BaseActivity() {

    private val binding by lazy {
        ActivityHomeScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpNavigation()
        supportActionBar?.hide()
    }

    private fun setUpNavigation() {
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
            .navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }
}
