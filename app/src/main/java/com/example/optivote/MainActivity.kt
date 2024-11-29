package com.example.optivote

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.optivote.databinding.ActivityViewPager1Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewPager1Binding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?)   {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPager1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = binding.myBottomVav
        val navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_,destination, _ ->
            when(destination.id){
                R.id.voteFragment ,R.id.historyDetails ->hideBottomNavigation()
                else->showBottomNavigation()
            }
        }




    }
    private fun showBottomNavigation() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNavigation() {
        bottomNavigationView.visibility = View.GONE
    }
}

