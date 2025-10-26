package com.bodeapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.data.model.Producto
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoScreen(navController: NavController) {

    // ✅ Inicializamos el ViewModel con Factory para que tenga acceso a la BD
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)

    // Estados locales del formulario
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    // Observamos los productos desde Room
    val productos by viewModel.productos.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registrar Producto") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🧾 Campos del formulario
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio (S/.)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock inicial") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && precio.isNotBlank() && stock.isNotBlank()) {
                        viewModel.addProducto(
                            nombre = nombre,
                            precio = precio.toDoubleOrNull() ?: 0.0,
                            stock = stock.toIntOrNull() ?: 0
                        )
                        nombre = ""
                        precio = ""
                        stock = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar producto")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Divider(thickness = 1.dp)
            Text(
                "📋 Productos registrados:",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            // 🧱 Lista de productos guardados (desde Room)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(productos) { p: Producto ->
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
                                Text(p.nombre, style = MaterialTheme.typography.titleMedium)
                                Text("Precio: S/. ${p.precio}")
                            }
                            Text("Stock: ${p.stock}")
                        }
                    }
                }
            }
        }
    }
}
