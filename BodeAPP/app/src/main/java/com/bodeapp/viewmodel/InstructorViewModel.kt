package com.bodeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bodeapp.data.dao.ProductoVendido
import com.bodeapp.data.model.Compra
import com.bodeapp.data.model.Producto
import com.bodeapp.data.model.Venta
import com.bodeapp.repository.ComprasRepository
import com.bodeapp.repository.ProductoRepository
import com.bodeapp.repository.VentasRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class InstructorViewModel(
    private val productoRepo: ProductoRepository,
    private val ventasRepo: VentasRepository,
    private val comprasRepo: ComprasRepository
) : ViewModel() {

    val productos: StateFlow<List<Producto>> = productoRepo.getProductos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val ventas: StateFlow<List<Venta>> = ventasRepo.getAllVentas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val compras: StateFlow<List<Compra>> = comprasRepo.getAllCompras()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getFechaActual(): String = dateFormat.format(Date())

    fun addProducto(nombre: String, precio: Double, stock: Int) {
        viewModelScope.launch {
            productoRepo.insertProducto(Producto(nombre = nombre, precio = precio, stock = stock))
        }
    }

    suspend fun verificarStockSuficiente(id: Int, cantidad: Int): Boolean {
        val producto = productoRepo.getProductoById(id)
        return producto != null && producto.stock >= cantidad
    }

    suspend fun registrarVenta(id: Int, cantidad: Int): Boolean {
        val producto = productoRepo.getProductoById(id)
        if (producto == null || producto.stock < cantidad) {
            return false
        }

        viewModelScope.launch {
            val subtotal = producto.precio * cantidad
            val venta = Venta(
                productoId = id,
                cantidad = cantidad,
                subtotal = subtotal,
                fecha = getFechaActual()
            )
            ventasRepo.insertVenta(venta)
            productoRepo.disminuirStock(id, cantidad)
        }
        return true
    }

    suspend fun registrarCompra(id: Int, cantidad: Int, costo: Double) {
        val compra = Compra(
            productoId = id,
            cantidad = cantidad,
            costo = costo,
            fecha = getFechaActual()
        )
        viewModelScope.launch {
            comprasRepo.insertCompra(compra)
            productoRepo.aumentarStock(id, cantidad)
        }
    }

    fun getTotalVentasDelDia(fecha: String = getFechaActual()): Flow<Double> =
        ventasRepo.getAllVentas().map { it.filter { v -> v.fecha == fecha }.sumOf { v -> v.subtotal } }

    fun getComprasDeLaSemana(): Flow<Double> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val inicioSemana = dateFormat.format(calendar.time)
        val finSemana = getFechaActual()
        return comprasRepo.getComprasPorFecha(inicioSemana, finSemana).map { 
            it.sumOf { c -> c.costo }
        }
    }

    fun getProductosMasVendidos(limite: Int = 10): Flow<List<ProductoVendido>> = flow {
        emit(ventasRepo.getProductosMasVendidos(limite))
    }

    suspend fun calcularCierreDeCaja(): Triple<Double, Double, Double> {
        val totalVentas = ventasRepo.getTotalVentas()
        val totalCompras = comprasRepo.getTotalCompras()
        val utilidad = (totalVentas ?: 0.0) - (totalCompras ?: 0.0)
        return Triple(totalVentas ?: 0.0, totalCompras ?: 0.0, utilidad)
    }
}
