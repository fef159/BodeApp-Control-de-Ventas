package com.bodeapp.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.data.dao.ProductoVendido
import com.bodeapp.presentation.navigation.BotonRegresar
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportesScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)
    val productos by viewModel.productos.collectAsState()
    val ventas by viewModel.ventas.collectAsState()

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var productoFiltro by remember { mutableStateOf<Int?>(null) }

    // Collect flows from ViewModel
    val reporteVentasDelDia by viewModel.getTotalVentasDelDia().collectAsState(initial = 0.0)
    val productosMasVendidos by viewModel.getProductosMasVendidos().collectAsState(initial = emptyList())
    val reporteComprasSemana by viewModel.getComprasDeLaSemana().collectAsState(initial = 0.0)

    // Cierre de caja (ventas, compras, utilidad)
    var cierreDeCaja by remember { mutableStateOf(Triple(0.0, 0.0, 0.0)) }

    val ventasFiltradas = remember(ventas, fechaInicio, fechaFin, productoFiltro) {
        if (fechaInicio.isEmpty() && fechaFin.isEmpty() && productoFiltro == null) {
            ventas
        } else {
            ventas.filter { v ->
                val fechaOk = (fechaInicio.isEmpty() || v.fecha >= fechaInicio) &&
                        (fechaFin.isEmpty() || v.fecha <= fechaFin)
                val productoOk = productoFiltro == null || v.productoId == productoFiltro
                fechaOk && productoOk
            }
        }
    }

    val totalVentasFiltradas = ventasFiltradas.sumOf { it.subtotal }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Reportes") }) }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            // Filtros
            Text("Filtros", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = fechaInicio,
                onValueChange = { fechaInicio = it },
                label = { Text("Fecha inicio (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = fechaFin,
                onValueChange = { fechaFin = it },
                label = { Text("Fecha fin (yyyy-MM-dd)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            Text("Filtrar por producto:", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            productos.take(5).forEach { producto ->
                FilterChip(
                    selected = productoFiltro == producto.id,
                    onClick = {
                        productoFiltro = if (productoFiltro == producto.id) null else producto.id
                    },
                    label = { Text(producto.nombre) },
                    modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                )
            }

            if (productoFiltro != null) {
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { productoFiltro = null },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Limpiar filtro")
                }
            }

            Divider(Modifier.padding(vertical = 16.dp))

            // Reportes
            Text("Reportes", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            // Ventas del día
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Ventas del Día (${dateFormat.format(Date())})",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "S/. ${"%.2f".format(reporteVentasDelDia)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Ventas filtradas
            if (fechaInicio.isNotEmpty() || fechaFin.isNotEmpty() || productoFiltro != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Ventas Filtradas",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "S/. ${"%.2f".format(totalVentasFiltradas)}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "${ventasFiltradas.size} ventas",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
            }

            // Compras de la semana
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Compras de la Semana",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "S/. ${"%.2f".format(reporteComprasSemana)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Productos más vendidos
            Text(
                "Productos Más Vendidos",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            if (productosMasVendidos.isEmpty()) {
                Text(
                    "No hay datos de productos más vendidos",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Column {
                    productosMasVendidos.take(5).forEach { item ->
                        val producto = productos.find { it.id == item.productoId }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        producto?.nombre ?: "Producto ${item.productoId}",
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        "${item.totalVendido} unidades vendidas",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Cierre de caja
            val coroutineScope = rememberCoroutineScope()
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text("Cierre de Caja", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Ventas Totales: S/. ${"%.2f".format(cierreDeCaja.first)}")
                    Text("Compras Totales: S/. ${"%.2f".format(cierreDeCaja.second)}")
                    Text("Utilidad: S/. ${"%.2f".format(cierreDeCaja.third)}", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Button(onClick = {
                            coroutineScope.launch {
                                cierreDeCaja = viewModel.calcularCierreDeCaja()
                            }
                        }) {
                            Text("Calcular cierre de caja")
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            BotonRegresar(navController)
        }
    }
}

