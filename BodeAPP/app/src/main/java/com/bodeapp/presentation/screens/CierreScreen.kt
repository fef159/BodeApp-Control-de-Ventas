package com.bodeapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.presentation.navigation.BotonRegresar
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CierreScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)
    val productos by viewModel.productos.collectAsState()
    val ventas by viewModel.ventas.collectAsState()
    val compras by viewModel.compras.collectAsState()

    var totalVentas by remember { mutableStateOf(0.0) }
    var totalCompras by remember { mutableStateOf(0.0) }
    var utilidad by remember { mutableStateOf(0.0) }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        isLoading = true
        val (ventas, compras, util) = viewModel.calcularCierreDeCaja()
        totalVentas = ventas
        totalCompras = compras
        utilidad = util
        isLoading = false
    }

    val totalInventario = productos.sumOf { it.precio * it.stock }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Cierre de Caja") }) }) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                Modifier
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Resumen del Cierre de Caja",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))

                // Sección de ventas
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "💰 Ventas Totales",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "S/. ${"%.2f".format(totalVentas)}",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            "Registradas: ${ventas.size} ventas",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // Sección de compras
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "📦 Compras Totales",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "S/. ${"%.2f".format(totalCompras)}",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            "Registradas: ${compras.size} compras",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // Utilidad
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (utilidad >= 0)
                            MaterialTheme.colorScheme.tertiaryContainer
                        else
                            MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            if (utilidad >= 0) "✅ Utilidad Neta" else "❌ Pérdida",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "S/. ${"%.2f".format(utilidad)}",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                Divider()

                // Valor de inventario
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "📊 Estado del Inventario",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Text("Productos registrados: ${productos.size}")
                        Text("Valor total en stock: S/. ${"%.2f".format(totalInventario)}")
                    }
                }

                Spacer(Modifier.height(20.dp))
                BotonRegresar(navController)
            }
        }
    }
}