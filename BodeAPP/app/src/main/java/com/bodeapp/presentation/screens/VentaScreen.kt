package com.bodeapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class VentaTemp(val producto: String, val cantidad: Int, val subtotal: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(navController: NavController) {
    var producto by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precioUnit by remember { mutableStateOf("") }
    var listaVentas by remember { mutableStateOf(listOf<VentaTemp>()) }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Registrar Venta") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = producto,
                onValueChange = { producto = it },
                label = { Text("Producto") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = precioUnit,
                onValueChange = { precioUnit = it },
                label = { Text("Precio Unitario (S/.)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val cant = cantidad.toIntOrNull() ?: 0
                    val precio = precioUnit.toDoubleOrNull() ?: 0.0
                    val subtotal = cant * precio
                    if (producto.isNotBlank() && cant > 0) {
                        listaVentas = listaVentas + VentaTemp(producto, cant, subtotal)
                        producto = ""
                        cantidad = ""
                        precioUnit = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar venta (temporal)")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("🧾 Ventas registradas:", style = MaterialTheme.typography.titleMedium)
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(top = 8.dp)
            ) {
                items(listaVentas) { v ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(v.producto)
                                Text("Cantidad: ${v.cantidad}")
                            }
                            Text("S/. ${v.subtotal}")
                        }
                    }
                }
            }
        }
    }
}
