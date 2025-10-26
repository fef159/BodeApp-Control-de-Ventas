package com.bodeapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bodeapp.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductosDao {
    @Insert
    suspend fun insert(producto: Producto)
    @Update
    suspend fun update(producto: Producto)
    @Query("SELECT * FROM productos")
    fun getAll(): Flow<List<Producto>>
}
