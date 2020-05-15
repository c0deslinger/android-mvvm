package com.paperplay.daggerpractice.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.paperplay.daggerpractice.R
import com.paperplay.daggerpractice.ui.BaseActivity

/**
 * Created by Ahmed Yusuf on 09/05/20.
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        private const val TAG = "MainActivity"
    }
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        initNav()
    }

    private fun initNav(){
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        navigationView.setNavigationItemSelectedListener(this)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                sessionManager.logout()
                return true
            }
            android.R.id.home -> {
                return if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }else{
                    false
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_profile -> {
                //prevent add to backstage on first fragment
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.main_navigation, true).build()
                Log.d(TAG, "onNavigationItemSelected: NavProfile")
                //navigate!
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.profileScreen, null, navOptions)
            }
            R.id.nav_post -> {
                //prevent add to backstage on first fragment
                if(isValidDestination(R.id.postScreen)) {
                    Log.d(TAG, "onNavigationItemSelected: NavPost")
                    //navigate!
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.postScreen)
                }
            }
        }
        item.isCheckable = true
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun isValidDestination(destination: Int): Boolean {
        //if current destination id is not target destination id
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment).currentDestination?.id ?: 0
    }

    override fun onSupportNavigateUp(): Boolean {
        //fix tombol hamburger button open drawer
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout)
    }
}