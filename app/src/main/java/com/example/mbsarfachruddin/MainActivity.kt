package com.example.mbsarfachruddin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.get
import com.example.mbsarfachruddin.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import dev.androidbroadcast.vbpd.viewBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavView = binding.bottomNavView
        bottomNavView.setupWithNavController(navController)

        setupBottomNavVisibility()
    }

    private fun setupBottomNavVisibility() {
        navController.addOnDestinationChangedListener {_, destination, _ ->
            when (destination.id) {
                R.id.splashFragment, R.id.loginFragment -> {toolbar.visibility = View.GONE; bottomNavView.visibility = View.GONE}

                R.id.studentHomeFragment -> {
                    toolbar.visibility = View.GONE
                    bottomNavView.visibility = View.VISIBLE
                }
                R.id.studentAnnouncementFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavView.visibility = View.VISIBLE
                }
                R.id.studentSettingFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavView.visibility = View.VISIBLE
                }

                R.id.musyrifHomeFragment -> {
                    toolbar.visibility = View.GONE
                    bottomNavView.visibility = View.VISIBLE
                }
                R.id.musyrifAnnouncementFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavView.visibility = View.VISIBLE
                }
                R.id.musyrifSettingFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavView.visibility = View.VISIBLE
                }

                R.id.teacherHomeFragment -> {
                    toolbar.visibility = View.GONE
                    bottomNavView.visibility = View.VISIBLE
                }
                R.id.teacherAnnouncementFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavView.visibility = View.VISIBLE
                }
                R.id.teacherSettingFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavView.visibility = View.VISIBLE
                }

                else -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavView.visibility = View.GONE
                }
            }

            val currentMenuId = bottomNavView.menu[0].itemId

            when (destination.id) {
                R.id.studentHomeFragment -> {
                    if (currentMenuId != R.id.studentHomeFragment) {
                        bottomNavView.menu.clear()
                        bottomNavView.inflateMenu(R.menu.student_bottom_menu)
                    }
                }
                R.id.musyrifHomeFragment -> {
                    if (currentMenuId != R.id.musyrifHomeFragment) {
                        bottomNavView.menu.clear()
                        bottomNavView.inflateMenu(R.menu.musyrif_bottom_menu)
                    }
                }
                R.id.teacherHomeFragment -> {
                    if (currentMenuId != R.id.teacherHomeFragment) {
                        bottomNavView.menu.clear()
                        bottomNavView.inflateMenu(R.menu.teacher_bottom_menu)
                    }
                }
            }
        }
    }
}