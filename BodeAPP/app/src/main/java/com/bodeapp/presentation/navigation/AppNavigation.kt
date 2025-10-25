package com.bodeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bodeapp.presentation.screens.CierreScreen
import com.bodeapp.presentation.screens.CompraScreen
import com.bodeapp.presentation.screens.HomeScreen
import com.bodeapp.presentation.screens.ProductoScreen
import com.bodeapp.presentation.screens.VentaScreen

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("productos") { ProductoScreen(navController) }
        composable("ventas") { VentaScreen(navController) }
        composable("compras") { CompraScreen(navController) }
        composable("cierre") { CierreScreen(navController) }
    }
}
