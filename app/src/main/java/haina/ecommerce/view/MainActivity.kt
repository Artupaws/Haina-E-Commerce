package haina.ecommerce.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import haina.ecommerce.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
//        val navController = findNavController(R.id.fragment)
//        bottomNavigationView.setupWithNavController(navController)

    }

}