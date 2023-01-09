package com.example.rickandmorty.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupNavigation()
    }

    private fun setupNavigation() = with(binding) {

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.charactersFragment, R.id.locationsFragment, R.id.episodesFragment
            )
        )
        toolbar.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //navigation.setupWithNavController(navController) работает так себе
        navigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.charactersFragment -> navController.navigate(R.id.charactersFragment)
                R.id.locationsFragment -> navController.navigate(R.id.locationsFragment)
                R.id.episodesFragment -> navController.navigate(R.id.episodesFragment)
            }
            true
        }
    }
}