package com.shadow.moviecatalogue.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shadow.moviecatalogue.R
import com.shadow.moviecatalogue.ui.BaseActivity
import com.shadow.moviecatalogue.ui.setting.SettingActivity

class MainActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
    }

    fun initComponent() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration.Builder(
            setOf(R.id.navigation_home, R.id.navigation_favorite, R.id.navigation_search)
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.home_action_button, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.language -> {
                val language = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(language)
            }
            R.id.setting -> {
                val setting = Intent(this, SettingActivity::class.java)
                startActivity(setting)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}