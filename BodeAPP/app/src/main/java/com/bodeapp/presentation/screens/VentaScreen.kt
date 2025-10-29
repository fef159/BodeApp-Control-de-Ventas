package com.bodeapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.data.model.Producto
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory
import kotlinx.coroutines.launch

data class ItemCarrito(
    val producto: Producto,
    val cantidad: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)

    val productos by viewModel.productos.collectAsState()
    val ventas by viewModel.ventas.collectAsState()

    val fechaHoy = viewModel.getFechaActual()
    val ventasHoy = ventas.filter { it.fecha == fechaHoy }

    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }
    var cantidad by remember { mutableStateOf("1") }
    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val totalCarrito = carrito.sumOf { it.producto.precio * it.cantidad }

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
                    currentRoute = "ventas",
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
            // Carrito de Compra
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Carrito de Compra",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Selecciona productos disponibles para vender",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selector de producto
                    Text("Producto", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Lista de productos
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        productos.filter { it.stock > 0 }.take(5).forEach { producto ->
                            Card(
                                onClick = {
                                    productoSeleccionado = producto
                                    errorMessage = null
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (productoSeleccionado?.id == producto.id)
                                        Color(0xFFEEF2FF)
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
                                            "Stock: ${producto.stock}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Text(
                                        "S/.${"%.2f".format(producto.precio)}",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF6366F1)
                                    )
                                }
                            }
                        }
                        
                        // Mostrar mensaje si no hay productos con stock
                        if (productos.filter { it.stock > 0 }.isEmpty()) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFEF3C7)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = Color(0xFFD97706),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            "No hay productos disponibles",
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp,
                                            color = Color(0xFFD97706)
                                        )
                                        Text(
                                            "Usa 'Compras' para abastecer el inventario",
                                            fontSize = 12.sp,
                                            color = Color(0xFFB45309)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Cantidad
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Cantidad", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = cantidad,
                                onValueChange = { cantidad = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón agregar
                    Button(
                        onClick = {
                            errorMessage = null
                            val prod = productoSeleccionado
                            val cant = cantidad.toIntOrNull() ?: 0

                            if (prod == null) {
                                errorMessage = "Selecciona un producto"
                            } else if (cant <= 0) {
                                errorMessage = "Cantidad debe ser mayor a 0"
                            } else if (prod.stock < cant) {
                                errorMessage = "Stock insuficiente"
                            } else {
                                carrito = carrito + ItemCarrito(prod, cant)
                                productoSeleccionado = null
                                cantidad = "1"
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agregar al Carrito")
                    }

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            errorMessage!!,
                            color = Color(0xFFEF4444),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista del carrito
                    if (carrito.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Carrito vacío",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            carrito.forEach { item ->
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFF9FAFB)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                item.producto.nombre,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                "Cantidad: ${item.cantidad}",
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                        }
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                "S/.${"%.2f".format(item.producto.precio * item.cantidad)}",
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF10B981)
                                            )
                                            IconButton(
                                                onClick = {
                                                    carrito = carrito.filter { it != item }
                                                }
                                            ) {
                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = "Eliminar",
                                                    tint = Color(0xFFEF4444)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Total
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF9FAFB)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(
                                "S/.${"%.2f".format(totalCarrito)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF10B981)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón completar venta
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                carrito.forEach { item ->
                                    viewModel.registrarVenta(item.producto.id, item.cantidad)
                                }
                                showSuccessDialog = true
                                carrito = emptyList()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = carrito.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6B7280),
                            disabledContainerColor = Color(0xFFE5E7EB)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Completar Venta")
                    }
                }
            }

            // Ventas de hoy
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Ventas de Hoy",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (ventasHoy.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No hay ventas registradas hoy",
                                color = Color.Gray
                            )
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            ventasHoy.takeLast(5).reversed().forEach { venta ->
                                val producto = productos.find { it.id == venta.productoId }
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFF9FAFB)
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
            title = { Text("Venta Registrada") },
            text = { 
                Column {
                    Text("La venta se registró correctamente")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "El stock se ha actualizado automáticamente",
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