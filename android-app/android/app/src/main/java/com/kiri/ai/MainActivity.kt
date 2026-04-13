package com.kiri.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kiri.ai.ui.screens.*
import com.kiri.ai.ui.theme.KiriTheme
import com.kiri.ai.ui.theme.Parchment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KiriTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Parchment
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(
                        navController = navController,
                        startDestination = "landing"
                    ) {
                        composable("landing") { LandingScreen(navController) }
                        composable("login") { LoginScreen(navController) }
                        composable("register") { RegisterScreen(navController) }
                        composable("chat") { ChatScreen(navController) }
                        composable("profile") { ProfileScreen(navController) }
                        composable("pricing") { PricingScreen(navController) }
                    }
                }
            }
        }
    }
}
