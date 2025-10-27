package com.bodeapp.repository

import com.bodeapp.data.dao.ComprasDao
import com.bodeapp.data.model.Compra
import kotlinx.coroutines.flow.Flow

class ComprasRepository(private val comprasDao: ComprasDao) {
    
    suspend fun insertCompra(compra: Compra) = comprasDao.insert(compra)
    
    fun getAllCompras(): Flow<List<Compra>> = comprasDao.getAll()
    
    fun getComprasPorFecha(fechaInicio: String, fechaFin: String): Flow<List<Compra>> = 
        comprasDao.getComprasPorFecha(fechaInicio, fechaFin)
    
    suspend fun getTotalComprasPorFecha(fechaInicio: String, fechaFin: String): Double = 
        comprasDao.getTotalComprasPorFecha(fechaInicio, fechaFin) ?: 0.0
    
    suspend fun getTotalCompras(): Double = comprasDao.getTotalCompras() ?: 0.0
}

