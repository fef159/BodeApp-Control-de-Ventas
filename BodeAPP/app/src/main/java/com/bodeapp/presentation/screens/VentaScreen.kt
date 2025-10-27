
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
fun VentasScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)
    val productos by viewModel.productos.collectAsState()

    var cantidad by remember { mutableStateOf("") }
    var productoSeleccionado by remember { mutableStateOf<Int?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Registrar Venta") }) }) { padding ->
        Column(Modifier.padding(padding).padding(20.dp)) {
            Text("Selecciona un producto:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            productos.forEach { p ->
                Card(
                    onClick = {
                        productoSeleccionado = p.id
                        errorMessage = null
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (p.id == productoSeleccionado)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("${p.nombre}", style = MaterialTheme.typography.titleSmall)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Stock: ${p.stock}", style = MaterialTheme.typography.bodyMedium)
                            Text("S/. ${"%.2f".format(p.precio)}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }

            if (productoSeleccionado != null) {
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = {
                        cantidad = it
                        errorMessage = null
                    },
                    label = { Text("Cantidad vendida") },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        val producto = productos.find { it.id == productoSeleccionado }
                        if (producto != null) {
                            Text("Máximo disponible: ${producto.stock}")
                        }
                    }
                )

                if (errorMessage != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        val c = cantidad.toIntOrNull() ?: 0
                        if (c <= 0) {
                            errorMessage = "La cantidad debe ser mayor a 0"
                        } else {
                            coroutineScope.launch {
                                val producto = productos.find { it.id == productoSeleccionado }
                                if (producto != null && producto.stock < c) {
                                    errorMessage = "Stock insuficiente. Solo hay ${producto.stock} unidades"
                                } else {
                                    val exito = viewModel.registrarVenta(productoSeleccionado!!, c)
                                    if (exito) {
                                        showSuccessDialog = true
                                        cantidad = ""
                                        productoSeleccionado = null
                                    } else {
                                        errorMessage = "Error al registrar la venta"
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar venta")
                }
            }

            Spacer(Modifier.height(20.dp))
            BotonRegresar(navController)
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Venta Registrada") },
            text = { Text("La venta se registró correctamente") },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}