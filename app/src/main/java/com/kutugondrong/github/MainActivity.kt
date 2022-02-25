package com.kutugondrong.github

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.kutugondrong.github.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bindingActivity: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        bindingActivity = binding
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        showToolbar(false)
    }

    fun showToolbar(isShow: Boolean) {
        if (isShow) {
            supportActionBar?.show()
        } else {
            supportActionBar?.hide()
        }
    }

    fun showHomeAsUpEnabled(isShowHomeAsUp: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isShowHomeAsUp)
    }

    override fun onBackPressed() {
        val currentDestId = navController.currentDestination?.id
        if (currentDestId == R.id.homeFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}