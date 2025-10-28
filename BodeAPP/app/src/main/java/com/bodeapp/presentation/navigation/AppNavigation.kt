package com.bodeapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bodeapp.presentation.screens.CierreScreen
import com.bodeapp.presentation.screens.HomeScreen
import com.bodeapp.presentation.screens.ProductoScreen
import com.bodeapp.presentation.screens.ComprasScreen
import com.bodeapp.presentation.screens.VentasScreen
import com.bodeapp.presentation.screens.ReportesScreen

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
        composable("cierre") { CierreScreen(navController) }
        composable("reportes") { ReportesScreen(navController) }
    }
}

@Composable
fun BotonRegresar(navController: androidx.navigation.NavController) {
    Button(
        onClick = { navController.popBackStack() },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Regresar al Menú Principal")
    }
    }
