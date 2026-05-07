package ru.avdemyanov.idoit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.color.DynamicColors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Поддержка Material You (динамические цвета)
        DynamicColors.applyToActivityIfAvailable(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (navController == null) {
            android.util.Log.e("MainActivity", "NavControllerNotFound")
        }
    }
}