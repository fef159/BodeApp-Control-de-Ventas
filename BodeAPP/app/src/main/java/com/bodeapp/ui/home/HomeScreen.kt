package com.bodeapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("BodeApp - Home") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Pantalla de inicio", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("productos") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Productos")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("ventas") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Ventas")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("compras") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Compras")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("cierre") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Cierre de Caja")
            }
        }
    }
}
