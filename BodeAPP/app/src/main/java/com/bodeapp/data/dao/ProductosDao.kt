package com.bodeapp.data.dao

import androidx.room.*
import com.bodeapp.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductosDao {
    @Insert suspend fun insert(producto: Producto)
    @Update suspend fun update(producto: Producto)
    @Delete suspend fun delete(producto: Producto)

    @Query("SELECT * FROM productos")
    fun getAll(): Flow<List<Producto>>
    
    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getById(id: Int): Producto?

    @Query("UPDATE productos SET stock = stock + :cantidad WHERE id = :id")
    suspend fun aumentarStock(id: Int, cantidad: Int)

    @Query("UPDATE productos SET stock = stock - :cantidad WHERE id = :id AND stock >= :cantidad")
    suspend fun disminuirStock(id: Int, cantidad: Int)
}
