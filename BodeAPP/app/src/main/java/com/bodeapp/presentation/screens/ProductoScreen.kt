package com.bodeapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.data.model.Producto
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun NuevoProductoDialog(
    producto: Producto? = null,
    onDismiss: () -> Unit,
    onConfirm: (String, Double, Int) -> Unit
) {
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var precio by remember { mutableStateOf(producto?.precio?.toString() ?: "") }
    var stock by remember { mutableStateOf(producto?.stock?.toString() ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = if (producto == null) "Nuevo Producto" else "Editar Producto",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del Producto") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio de Venta (S/.)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stock Inicial") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    enabled = producto != null, // Solo editable si es producto existente
                    placeholder = { Text(if (producto == null) "Se creará con stock = 0" else "Stock actual") }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = {
                            if (nombre.isNotBlank()) {
                                val stockValue = if (producto == null) {
                                    0 // Nuevo producto siempre con stock = 0
                                } else {
                                    stock.toIntOrNull() ?: producto.stock
                                }
                                onConfirm(
                                    nombre,
                                    precio.toDoubleOrNull() ?: 0.0,
                                    stockValue
                                )
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        )
                    ) {
                        Text(if (producto == null) "Crear Producto" else "Actualizar")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "Stock",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                producto.stock.toString(),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                        Column {
                            Text(
                                "Precio",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                "S/." + String.format("%.2f", producto.precio),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF10B981)
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = Color(0xFF6366F1)
                        )
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color(0xFFEF4444)
                        )
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Producto") },
            text = { Text("¿Estás seguro de eliminar " + producto.nombre + "?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar", color = Color(0xFFEF4444))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)

    val productos by viewModel.productos.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var productoEditando by remember { mutableStateOf<Producto?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val productosFiltrados = remember(productos, searchQuery) {
        if (searchQuery.isEmpty()) {
            productos
        } else {
            productos.filter { it.nombre.contains(searchQuery, ignoreCase = true) }
        }
    }

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
                    currentRoute = "productos",
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    productoEditando = null
                    showDialog = true
                },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Producto")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Inventario de Productos",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Gestiona tu catálogo de productos. Los nuevos productos se crean con stock = 0",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar productos...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6366F1),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (productosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Inventory,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (searchQuery.isEmpty())
                                "No hay productos registrados"
                            else
                                "No se encontraron productos",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (searchQuery.isEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Usa 'Compras' para abastecer el stock de tus productos",
                                color = Color(0xFF6366F1),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productosFiltrados) { producto ->
                        ProductoCard(
                            producto = producto,
                            onEdit = {
                                productoEditando = producto
                                showDialog = true
                            },
                            onDelete = {
                                viewModel.deleteProducto(producto)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        NuevoProductoDialog(
            producto = productoEditando,
            onDismiss = {
                showDialog = false
                productoEditando = null
            },
            onConfirm = { nombre, precio, stock ->
                if (productoEditando != null) {
                    coroutineScope.launch {
                        viewModel.updateProducto(
                            productoEditando!!.copy(
                                nombre = nombre,
                                precio = precio,
                                stock = stock
                            )
                        )
                    }
                } else {
                    // Nuevo producto siempre con stock = 0
                    viewModel.addProducto(nombre, precio, 0)
                }
                showDialog = false
                productoEditando = null
            }
        )
    }
}