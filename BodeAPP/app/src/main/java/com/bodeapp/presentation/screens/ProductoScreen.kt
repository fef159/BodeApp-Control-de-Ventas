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
import com.bodeapp.presentation.navigation.BotonRegresar
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory

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
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(productos) { p ->
                    ListItem(
                        headlineContent = { Text(p.nombre) },
                        supportingContent = { Text("Precio: S/.${p.precio} | Stock: ${p.stock}") }
                    )
                    Divider()
                }
            }
            BotonRegresar(navController)
        }
    }
}
