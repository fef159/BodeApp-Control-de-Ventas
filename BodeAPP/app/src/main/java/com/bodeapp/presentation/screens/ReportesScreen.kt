package com.bodeapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
    val compras by viewModel.compras.collectAsState()
    
    val coroutineScope = rememberCoroutineScope()

    val dateFormat = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "PE"))
    val fechaHoy = viewModel.getFechaActual()
    var fechaSeleccionada by remember { mutableStateOf(fechaHoy) }

    val ventasDia = ventas.filter { it.fecha == fechaSeleccionada }
    val comprasDia = compras.filter { it.fecha == fechaSeleccionada }

    val ingresosDia = ventasDia.sumOf { it.subtotal }
    val gastosDia = comprasDia.sumOf { it.costo }
    val gananciaDia = ingresosDia - gastosDia
    val margenDia = if (ingresosDia > 0) (gananciaDia / ingresosDia * 100) else 0.0

    // Totales generales
    val totalProductos = productos.size
    val ingresosGenerales = ventas.sumOf { it.subtotal }
    val gastosGenerales = compras.sumOf { it.costo }
    val gananciaGeneral = ingresosGenerales - gastosGenerales

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "reportes",
                onNavigate = { route -> navController.navigate(route) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Cierre de Caja y Reportes",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Consulta el resumen financiero por fecha",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Selector de fecha
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = Color(0xFF6366F1)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                try {
                                    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                        .parse(fechaSeleccionada)
                                    dateFormat.format(date ?: Date())
                                } catch (e: Exception) {
                                    fechaSeleccionada
                                },
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (fechaSeleccionada != fechaHoy) {
                                OutlinedButton(
                                    onClick = { fechaSeleccionada = fechaHoy },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Hoy")
                                }
                            }
                        }
                    }
                }
            }

            // Métricas del día
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.TrendingUp,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(32.dp)
                            )
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    "Ingresos del Día",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Text(
                                    "S/.${"%.2f".format(ingresosDia)}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF10B981)
                                )
                                Text(
                                    "${ventasDia.size} transacciones",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.TrendingDown,
                                contentDescription = null,
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(32.dp)
                            )
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    "Gastos del Día",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Text(
                                    "S/.${"%.2f".format(gastosDia)}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFEF4444)
                                )
                                Text(
                                    "${comprasDia.size} compras",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.AttachMoney,
                                contentDescription = null,
                                tint = Color(0xFF3B82F6),
                                modifier = Modifier.size(32.dp)
                            )
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    "Ganancia del Día",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Text(
                                    "S/.${"%.2f".format(gananciaDia)}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (gananciaDia >= 0) Color(0xFF3B82F6) else Color(0xFFEF4444)
                                )
                                Text(
                                    "Margen: ${"%.0f".format(margenDia)}%",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }

            // Detalle de Ventas
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            "Detalle de Ventas",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        if (ventasDia.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "No hay ventas en esta fecha",
                                    color = Color.Gray
                                )
                            }
                        } else {
                            ventasDia.forEach { venta ->
                                val producto = productos.find { it.id == venta.productoId }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            producto?.nombre ?: "Producto ${venta.productoId}",
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            "Cantidad: ${venta.cantidad}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Text(
                                        "S/.${"%.2f".format(venta.subtotal)}",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF10B981)
                                    )
                                }
                                if (venta != ventasDia.last()) {
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }
            }

            // Detalle de Compras
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            "Detalle de Compras",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        if (comprasDia.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "No hay compras en esta fecha",
                                    color = Color.Gray
                                )
                            }
                        } else {
                            comprasDia.forEach { compra ->
                                val producto = productos.find { it.id == compra.productoId }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            producto?.nombre ?: "Producto ${compra.productoId}",
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            "Cantidad: ${compra.cantidad}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Text(
                                        "S/.${"%.2f".format(compra.costo)}",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFEF4444)
                                    )
                                }
                                if (compra != comprasDia.last()) {
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }
            }

            // Resumen General
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                    elevation = CardDefaults.cardElevation(2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Resumen General",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            
                            // Botón de limpiar datos
                            var showClearDialog by remember { mutableStateOf(false) }
                            
                            Button(
                                onClick = { showClearDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFEF4444)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.DeleteForever,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Limpiar", fontSize = 12.sp)
                            }
                            
                            if (showClearDialog) {
                                AlertDialog(
                                    onDismissRequest = { showClearDialog = false },
                                    title = { 
                                        Text(
                                            "⚠️ Limpiar Todos los Datos",
                                            color = Color(0xFFEF4444)
                                        ) 
                                    },
                                    text = { 
                                        Column {
                                            Text("Esta acción eliminará TODOS los datos:")
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text("• Todos los productos", fontWeight = FontWeight.Medium)
                                            Text("• Todas las ventas", fontWeight = FontWeight.Medium)
                                            Text("• Todas las compras", fontWeight = FontWeight.Medium)
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                "Esta acción NO se puede deshacer",
                                                color = Color(0xFFEF4444),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    },
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    viewModel.limpiarTodosLosDatos()
                                                    showClearDialog = false
                                                }
                                            },
                                            colors = ButtonDefaults.textButtonColors(
                                                contentColor = Color(0xFFEF4444)
                                            )
                                        ) {
                                            Text("ELIMINAR TODO")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = { showClearDialog = false }) {
                                            Text("Cancelar")
                                        }
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Inventory,
                                    contentDescription = null,
                                    tint = Color(0xFF6366F1),
                                    modifier = Modifier.size(28.dp)
                                )
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        "Productos",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        totalProductos.toString(),
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.TrendingUp,
                                    contentDescription = null,
                                    tint = Color(0xFF10B981),
                                    modifier = Modifier.size(28.dp)
                                )
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        "Ingresos Totales",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        "S/.${"%.0f".format(ingresosGenerales)}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF10B981)
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.TrendingDown,
                                    contentDescription = null,
                                    tint = Color(0xFFEF4444),
                                    modifier = Modifier.size(28.dp)
                                )
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        "Gastos Totales",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        "S/.${"%.0f".format(gastosGenerales)}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFEF4444)
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.AccountBalance,
                                    contentDescription = null,
                                    tint = Color(0xFF3B82F6),
                                    modifier = Modifier.size(28.dp)
                                )
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        "Ganancia Total",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        "S/.${"%.0f".format(gananciaGeneral)}",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF3B82F6)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}