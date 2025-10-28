package com.bodeapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.data.model.Producto
import com.bodeapp.presentation.navigation.BotonRegresar
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun EditarProductoDialog(
    producto: Producto,
    onConfirm: (Producto) -> Unit,
    onDismiss: () -> Unit
) {
    var nombre by remember { mutableStateOf(producto.nombre) }
    var precio by remember { mutableStateOf(producto.precio.toString()) }
    var stock by remember { mutableStateOf(producto.stock.toString()) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Producto") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stock") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val productoActualizado = Producto(
                        id = producto.id,
                        nombre = nombre,
                        precio = precio.toDoubleOrNull() ?: 0.0,
                        stock = stock.toIntOrNull() ?: 0
                    )
                    onConfirm(productoActualizado)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun ProductoItem(
    producto: Producto,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Precio: S/.${String.format("%.2f", producto.precio)} | Stock: ${producto.stock}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
    
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Producto") },
            text = { Text("¿Estás seguro de eliminar ${producto.nombre}?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
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

    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    val productos by viewModel.productos.collectAsState()
    
    var productoEditando by remember { mutableStateOf<Producto?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    
    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text("Registrar Producto") })
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock inicial") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank()) {
                        viewModel.addProducto(
                            nombre,
                            precio.toDoubleOrNull() ?: 0.0,
                            stock.toIntOrNull() ?: 0
                        )
                        nombre = ""; precio = ""; stock = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar producto")
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Text("📋 Lista de productos:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productos) { p ->
                    ProductoItem(
                        producto = p,
                        onEdit = { 
                            productoEditando = p
                            showEditDialog = true
                        },
                        onDelete = { viewModel.deleteProducto(p) }
                    )
                }
            }
            
            // Diálogo de edición
            if (showEditDialog && productoEditando != null) {
                EditarProductoDialog(
                    producto = productoEditando!!,
                    onConfirm = { productoActualizado ->
                        coroutineScope.launch {
                            viewModel.updateProducto(productoActualizado)
                        }
                        showEditDialog = false
                        productoEditando = null
                    },
                    onDismiss = {
                        showEditDialog = false
                        productoEditando = null
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            BotonRegresar(navController)
        }
    }
}
