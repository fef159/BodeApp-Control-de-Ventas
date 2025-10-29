package com.bodeapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)

    val productos by viewModel.productos.collectAsState()
    val compras by viewModel.compras.collectAsState()

    val fechaHoy = viewModel.getFechaActual()
    val comprasHoy = compras.filter { it.fecha == fechaHoy }
    val totalComprasHoy = comprasHoy.sumOf { it.costo }

    var articulo by remember { mutableStateOf("") }
    var proveedor by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var costoUnitario by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

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
                    currentRoute = "compras",
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nueva Compra
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Nueva Compra",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Registra compras para abastecer tu inventario",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Artículo / Insumo
                    Text(
                        "Artículo / Insumo",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = articulo,
                        onValueChange = { articulo = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Ej: Refresco Coca-Cola, Papas Fritas", fontSize = 14.sp) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE5E7EB)
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Proveedor
                    Text(
                        "Proveedor",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = proveedor,
                        onValueChange = { proveedor = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Nombre del proveedor", fontSize = 14.sp) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE5E7EB)
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Cantidad y Costo
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Cantidad",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = cantidad,
                                onValueChange = { cantidad = it },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0xFFE5E7EB)
                                )
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Costo Unit.",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = costoUnitario,
                                onValueChange = { costoUnitario = it },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0xFFE5E7EB)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón registrar
                    Button(
                        onClick = {
                            val cant = cantidad.toIntOrNull() ?: 0
                            val costo = costoUnitario.toDoubleOrNull() ?: 0.0

                            if (articulo.isNotBlank() && cant > 0 && costo > 0) {
                                coroutineScope.launch {
                                    val productoExistente = productos.find {
                                        it.nombre.equals(articulo, ignoreCase = true)
                                    }

                                    val productoId = if (productoExistente != null) {
                                        productoExistente.id
                                    } else {
                                        // Crear nuevo producto con precio de venta sugerido (30% margen)
                                        viewModel.addProducto(articulo, costo * 1.3, 0)
                                        productos.find { it.nombre.equals(articulo, ignoreCase = true) }?.id ?: 0
                                    }

                                    if (productoId > 0) {
                                        viewModel.registrarCompra(productoId, cant, costo * cant)
                                        showSuccessDialog = true
                                        articulo = ""
                                        proveedor = ""
                                        cantidad = ""
                                        costoUnitario = ""
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = articulo.isNotBlank() &&
                                cantidad.toIntOrNull() ?: 0 > 0 &&
                                costoUnitario.toDoubleOrNull() ?: 0.0 > 0,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            disabledContainerColor = Color(0xFFE5E7EB)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Registrar Compra")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista de productos disponibles
                    Text(
                        "Productos Existentes:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Toca un producto para reabastecerlo",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        productos.take(5).forEach { producto ->
                            Card(
                                onClick = { articulo = producto.nombre },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (producto.stock == 0) 
                                        Color(0xFFFEF3C7) // Amarillo para stock 0
                                    else 
                                        Color(0xFFF9FAFB)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            producto.nombre,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            "Precio: S/.${"%.2f".format(producto.precio)}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (producto.stock == 0)
                                                Color(0xFFFEE2E2)
                                            else if (producto.stock < 10)
                                                Color(0xFFFEF3C7)
                                            else
                                                Color(0xFFD1FAE5)
                                        )
                                    ) {
                                        Text(
                                            text = if (producto.stock == 0) "Sin Stock" else "Stock: ${producto.stock}",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            fontWeight = FontWeight.Bold,
                                            color = if (producto.stock == 0)
                                                Color(0xFFDC2626)
                                            else if (producto.stock < 10)
                                                Color(0xFFD97706)
                                            else
                                                Color(0xFF059669),
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Compras de Hoy
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Compras de Hoy",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFEE2E2)
                            )
                        ) {
                            Text(
                                text = "Total: S/.${"%.2f".format(totalComprasHoy)}",
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFDC2626),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (comprasHoy.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No hay compras registradas",
                                color = Color.Gray
                            )
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            comprasHoy.reversed().forEach { compra ->
                                val producto = productos.find { it.id == compra.productoId }
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFF9FAFB)
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                producto?.nombre ?: "Producto ${compra.productoId}",
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 14.sp,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                "S/.${"%.2f".format(compra.costo)}",
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFFEF4444),
                                                fontSize = 14.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                "Cantidad: ${compra.cantidad}",
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                            Text(
                                                "Costo unit: S/.${"%.2f".format(compra.costo / compra.cantidad)}",
                                                fontSize = 12.sp,
                                                color = Color.Gray
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
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Compra Registrada") },
            text = { 
                Column {
                    Text("La compra se registró correctamente")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "El stock del producto se ha actualizado automáticamente",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}