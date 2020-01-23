package com.shadow.favoritecataloguemovie.ui.main

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.tabs.TabLayout
import com.shadow.favoritecataloguemovie.R
import com.shadow.favoritecataloguemovie.ui.BaseActivity
import com.shadow.favoritecataloguemovie.util.adapter.FavoriteTabAdapter
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
    }

    fun initComponent() {
        val tabAdapter = FavoriteTabAdapter(baseContext, supportFragmentManager)
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_movie)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_tv)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab) {
                p0.getIcon()
                    ?.setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN)
            }

            override fun onTabSelected(p0: TabLayout.Tab) {
                p0.getIcon()
                    ?.setColorFilter(Color.parseColor("#E64525"), PorterDuff.Mode.SRC_IN)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.home_action_button, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.language -> {
                val language = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(language)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}