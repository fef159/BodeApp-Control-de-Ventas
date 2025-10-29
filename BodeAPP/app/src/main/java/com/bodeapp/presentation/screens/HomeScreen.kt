package com.bodeapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory

@Composable
fun TopNavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NavigationTab(
            icon = Icons.Default.Home,
            label = "Inicio",
            isSelected = currentRoute == "home",
            onClick = { onNavigate("home") }
        )
        NavigationTab(
            icon = Icons.Default.Inventory,
            label = "Productos",
            isSelected = currentRoute == "productos",
            onClick = { onNavigate("productos") }
        )
        NavigationTab(
            icon = Icons.Default.ShoppingCart,
            label = "Ventas",
            isSelected = currentRoute == "ventas",
            onClick = { onNavigate("ventas") }
        )
        NavigationTab(
            icon = Icons.Default.ShoppingBag,
            label = "Compras",
            isSelected = currentRoute == "compras",
            onClick = { onNavigate("compras") }
        )
        NavigationTab(
            icon = Icons.Default.Description,
            label = "Reportes",
            isSelected = currentRoute == "reportes",
            onClick = { onNavigate("reportes") }
        )
    }
}

@Composable
fun RowScope.NavigationTab(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.White else Color.Transparent,
            contentColor = if (isSelected) Color.Black else Color.Gray
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = if (isSelected) ButtonDefaults.buttonElevation(2.dp) else ButtonDefaults.buttonElevation(0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    backgroundColor: Color = Color.White
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)

    val productos by viewModel.productos.collectAsState()
    val ventas by viewModel.ventas.collectAsState()
    val compras by viewModel.compras.collectAsState()

    val fechaHoy = viewModel.getFechaActual()
    val ventasHoy = ventas.filter { it.fecha == fechaHoy }
    val comprasHoy = compras.filter { it.fecha == fechaHoy }

    val totalVentasHoy = ventasHoy.sumOf { it.subtotal }
    val totalComprasHoy = comprasHoy.sumOf { it.costo }
    val gananciaHoy = totalVentasHoy - totalComprasHoy
    val totalStock = productos.sumOf { it.stock }

    val productosStockBajo = productos.filter { it.stock < 10 }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Sistema de Gestión de Bodega",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White
                    )
                )
                TopNavigationBar(
                    currentRoute = "home",
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
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
                    text = "Dashboard",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Resumen general de tu bodega",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetricCard(
                            title = "Productos Totales",
                            value = productos.size.toString(),
                            subtitle = "$totalStock unidades en stock",
                            icon = Icons.Default.Inventory,
                            iconTint = Color(0xFF6366F1)
                        )
                        MetricCard(
                            title = "Gastos Hoy",
                            value = "S/.${"%.2f".format(totalComprasHoy)}",
                            subtitle = "${comprasHoy.size} compras",
                            icon = Icons.Default.TrendingDown,
                            iconTint = Color(0xFFEF4444)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetricCard(
                            title = "Ventas Hoy",
                            value = "S/.${"%.2f".format(totalVentasHoy)}",
                            subtitle = "${ventasHoy.size} transacciones",
                            icon = Icons.Default.TrendingUp,
                            iconTint = Color(0xFF10B981)
                        )
                        MetricCard(
                            title = "Ganancia Hoy",
                            value = "S/.${"%.2f".format(gananciaHoy)}",
                            subtitle = "Total: S/.${"%.2f".format(totalVentasHoy)}",
                            icon = Icons.Default.AttachMoney,
                            iconTint = if (gananciaHoy >= 0) Color(0xFF3B82F6) else Color(0xFFEF4444)
                        )
                    }
                }
            }

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
                            text = "Últimas Ventas",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        if (ventasHoy.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No hay ventas registradas",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            ventasHoy.takeLast(5).reversed().forEach { venta ->
                                val producto = productos.find { it.id == venta.productoId }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = producto?.nombre ?: "Producto #${venta.productoId}",
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "Cantidad: ${venta.cantidad}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray
                                        )
                                    }
                                    Text(
                                        text = "S/.${"%.2f".format(venta.subtotal)}",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF10B981)
                                    )
                                }
                                if (venta != ventasHoy.takeLast(5).reversed().last()) {
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }
            }

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
                            text = "Productos con Bajo Stock",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        if (productosStockBajo.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Todos los productos tienen stock suficiente",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            productosStockBajo.take(5).forEach { producto ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = producto.nombre,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "Precio: S/.${"%.2f".format(producto.precio)}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray
                                        )
                                    }
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (producto.stock < 5)
                                                Color(0xFFFEE2E2)
                                            else
                                                Color(0xFFFEF3C7)
                                        )
                                    ) {
                                        Text(
                                            text = "Stock: ${producto.stock}",
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                            fontWeight = FontWeight.Bold,
                                            color = if (producto.stock < 5)
                                                Color(0xFFDC2626)
                                            else
                                                Color(0xFFD97706),
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                                if (producto != productosStockBajo.take(5).last()) {
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}