package com.bodeapp.data.dao

import androidx.room.*
import com.bodeapp.data.model.Compra
import kotlinx.coroutines.flow.Flow

@Dao
interface ComprasDao {
    @Insert suspend fun insert(compra: Compra)
    
    @Query("SELECT * FROM compras")
    fun getAll(): Flow<List<Compra>>
    
    @Query("SELECT * FROM compras WHERE fecha BETWEEN :fechaInicio AND :fechaFin")
    fun getComprasPorFecha(fechaInicio: String, fechaFin: String): Flow<List<Compra>>
    
    @Query("SELECT SUM(costo) FROM compras WHERE fecha BETWEEN :fechaInicio AND :fechaFin")
    suspend fun getTotalComprasPorFecha(fechaInicio: String, fechaFin: String): Double?
    
    @Query("SELECT SUM(costo) FROM compras")
    suspend fun getTotalCompras(): Double?
}

