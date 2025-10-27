package com.bodeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ventas")
data class Venta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoId: Int,
    val cantidad: Int,
    val subtotal: Double,
    val fecha: String
)


