package com.bodeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bodeapp.presentation.screens.*

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("productos") { ProductoScreen(navController) }
        composable("ventas") { VentasScreen(navController) }
        composable("compras") { ComprasScreen(navController) }
        composable("reportes") { ReportesScreen(navController) }
    }
}