package com.bodeapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bodeapp.viewmodel.InstructorViewModel
import com.bodeapp.viewmodel.InstructorViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(navController: NavController) {
    val context = LocalContext.current
    val factory = remember { InstructorViewModelFactory(context) }
    val viewModel: InstructorViewModel = viewModel(factory = factory)
    val productos by viewModel.productos.collectAsState()

    var cantidad by remember { mutableStateOf("") }
    var productoSeleccionado by remember { mutableStateOf<Int?>(null) }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Registrar Venta") }) }) { padding ->
        Column(Modifier.padding(padding).padding(20.dp)) {
            Text("Selecciona un producto:")

            productos.forEach { p ->
                Button(
                    onClick = { productoSeleccionado = p.id },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text("${p.nombre} (Stock: ${p.stock})")
                }
            }

            if (productoSeleccionado != null) {
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad vendida") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
                )
                Button(
                    onClick = {
                        val c = cantidad.toIntOrNull() ?: 0
                        if (c > 0) viewModel.venderProducto(productoSeleccionado!!, c)
                        cantidad = ""
                        productoSeleccionado = null
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar venta")
                }
            }
        }
    }
}