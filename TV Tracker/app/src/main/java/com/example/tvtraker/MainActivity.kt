package com.example.tvtraker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tvtraker.util.UserPreferences
import com.example.tvtraker.util.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var preferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = UserPreferences(this)
        val navHostfragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostfragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.serijeFragment, R.id.profilFragment)
        )

        setSupportActionBar(toolbar)
        visibilityNavElements(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottom_nav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lifecycleScope.launch {
            preferences.saveLoginKey("")
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment,
                R.id.registerFragment,
                R.id.splashScreenFragment -> {
                    toolbar.visible(false)
                    bottom_nav.visible(false)
                }
                R.id.serijeDetailFragment ->{
                    toolbar.visible(true)
                    bottom_nav.visible(false)
                }
                R.id.profilSerijeDetailFragment -> {
                    toolbar.visible(true)
                    bottom_nav.visible(false)
                }
                else -> {
                    toolbar.visible(true)
                    bottom_nav.visible(true)
                }
            }
        }
    }
}