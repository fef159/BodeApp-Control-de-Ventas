package com.bodeapp.data.dao

import androidx.room.*
import com.bodeapp.data.model.Venta
import kotlinx.coroutines.flow.Flow

@Dao
interface VentasDao {
    @Insert suspend fun insert(venta: Venta)
    
    @Query("SELECT * FROM ventas")
    fun getAll(): Flow<List<Venta>>
    
    @Query("SELECT * FROM ventas WHERE fecha = :fecha")
    fun getVentasDelDia(fecha: String): Flow<List<Venta>>
    
    @Query("SELECT * FROM ventas WHERE fecha BETWEEN :fechaInicio AND :fechaFin")
    fun getVentasPorFecha(fechaInicio: String, fechaFin: String): Flow<List<Venta>>
    
    @Query("SELECT SUM(subtotal) FROM ventas WHERE fecha = :fecha")
    suspend fun getTotalVentasDelDia(fecha: String): Double?
    
    @Query("SELECT productoId, SUM(cantidad) as totalVendido FROM ventas GROUP BY productoId ORDER BY totalVendido DESC LIMIT :limite")
    suspend fun getProductosMasVendidos(limite: Int): List<ProductoVendido>
    
    @Query("SELECT SUM(subtotal) FROM ventas")
    suspend fun getTotalVentas(): Double?
    
    @Query("DELETE FROM ventas")
    suspend fun deleteAll()
}

data class ProductoVendido(
    val productoId: Int,
    val totalVendido: Int
)

