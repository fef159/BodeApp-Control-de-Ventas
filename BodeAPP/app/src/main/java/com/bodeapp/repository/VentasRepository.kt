package com.bodeapp.repository

import com.bodeapp.data.dao.ProductoVendido
import com.bodeapp.data.dao.VentasDao
import com.bodeapp.data.model.Venta
import kotlinx.coroutines.flow.Flow

class VentasRepository(private val ventasDao: VentasDao) {
    
    suspend fun insertVenta(venta: Venta) = ventasDao.insert(venta)
    
    fun getAllVentas(): Flow<List<Venta>> = ventasDao.getAll()
    
    fun getVentasDelDia(fecha: String): Flow<List<Venta>> = ventasDao.getVentasDelDia(fecha)
    
    fun getVentasPorFecha(fechaInicio: String, fechaFin: String): Flow<List<Venta>> = 
        ventasDao.getVentasPorFecha(fechaInicio, fechaFin)
    
    suspend fun getTotalVentasDelDia(fecha: String): Double = ventasDao.getTotalVentasDelDia(fecha) ?: 0.0
    
    suspend fun getProductosMasVendidos(limite: Int): List<ProductoVendido> = 
        ventasDao.getProductosMasVendidos(limite)
    
    suspend fun getTotalVentas(): Double = ventasDao.getTotalVentas() ?: 0.0
    
    suspend fun deleteAllVentas() = ventasDao.deleteAll()
}

