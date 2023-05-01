package com.example.inventoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.inventoryapp.databinding.ActivityMainBinding

// TODO: When app is re-shown , it throws to MainFragment from AddFragment and EditFragment
// TODO: Add Archive
// TODO: Add taking picture
// TODO: Add saving state for AddFragment image. If image is selected and when user tries to reselect another img and doesn't actually choose anything and goes back it removes prev img
// TODO: MVP - should I use diff ViewContracts and Presenter for each Frag? For ex: In EditFrag there is a func getAllShoes() coz its extended from ShoesView
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }
}