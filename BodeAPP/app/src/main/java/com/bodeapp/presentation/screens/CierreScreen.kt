package com.bodeapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CierreScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)
    val productos by viewModel.productos.collectAsState()

    val totalInventario = productos.sumOf { it.precio * it.stock }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Cierre de Caja") }) }) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Resumen del Inventario", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            Text("Productos registrados: ${productos.size}")
            Text("Valor total estimado en stock: S/. ${"%.2f".format(totalInventario)}")
            Spacer(Modifier.height(20.dp))
            Text("💰 Utilidad (simulada): S/. ${"%.2f".format(totalInventario * 0.3)}")
        }
    }
}