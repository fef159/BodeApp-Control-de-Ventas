package com.bodeapp.repository

import com.bodeapp.data.dao.ProductosDao
import com.bodeapp.data.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val dao: ProductosDao) {
    fun getProductos(): Flow<List<Producto>> = dao.getAll()
    suspend fun insertProducto(producto: Producto) = dao.insert(producto)
    suspend fun updateProducto(producto: Producto) = dao.update(producto)
}
