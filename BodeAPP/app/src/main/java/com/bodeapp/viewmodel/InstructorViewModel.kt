package com.bodeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bodeapp.data.model.Producto
import com.bodeapp.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InstructorViewModel(private val repo: ProductoRepository) : ViewModel() {

    val productos: StateFlow<List<Producto>> = repo.getProductos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addProducto(nombre: String, precio: Double, stock: Int) {
        viewModelScope.launch {
            repo.insertProducto(Producto(nombre = nombre, precio = precio, stock = stock))
        }
    }
}
