package com.bodeapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun ComprasScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)
    val productos by viewModel.productos.collectAsState()

    var cantidad by remember { mutableStateOf("") }
    var costoTotal by remember { mutableStateOf("") }
    var productoSeleccionado by remember { mutableStateOf<Int?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    
    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Registrar Compra") }) }) { padding ->
        Column(Modifier.padding(padding).padding(20.dp)) {
            Text("Selecciona un producto:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            productos.forEach { p ->
                Card(
                    onClick = { productoSeleccionado = p.id },
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
                        Text("Stock actual: ${p.stock}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            if (productoSeleccionado != null) {
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad comprada") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = costoTotal,
                    onValueChange = { costoTotal = it },
                    label = { Text("Costo total (S/.)") },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = { Text("Ingresa el costo total de la compra") }
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        val c = cantidad.toIntOrNull() ?: 0
                        val costo = costoTotal.toDoubleOrNull() ?: 0.0
                        if (c > 0 && costo > 0) {
                            coroutineScope.launch {
                                viewModel.registrarCompra(productoSeleccionado!!, c, costo)
                                showSuccessDialog = true
                                cantidad = ""
                                costoTotal = ""
                                productoSeleccionado = null
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = cantidad.toIntOrNull() ?: 0 > 0 && costoTotal.toDoubleOrNull() ?: 0.0 > 0
                ) {
                    Text("Registrar compra")
                }
            }
            
            Spacer(Modifier.height(20.dp))
            BotonRegresar(navController)
        }
    }
    
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Compra Registrada") },
            text = { Text("La compra se registró correctamente y se actualizó el stock") },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}