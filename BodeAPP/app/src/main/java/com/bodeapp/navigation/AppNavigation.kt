package com.bodeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bodeapp.cierre.compras.CierreScreen
import com.bodeapp.ui.compras.CompraScreen
import com.bodeapp.ui.home.HomeScreen
import com.bodeapp.ui.productos.ProductoScreen
import com.bodeapp.ventas.productos.VentaScreen

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
