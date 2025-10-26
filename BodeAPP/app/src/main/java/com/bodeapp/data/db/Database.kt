package com.bodeapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bodeapp.data.dao.ProductosDao
import com.bodeapp.data.model.Compra
import com.bodeapp.data.model.Producto
import com.bodeapp.data.model.Venta

@Database(
    entities = [Producto::class, Venta::class, Compra::class],
    version = 1,
    exportSchema = false
)
abstract class BodeAppDatabase : RoomDatabase() {
    abstract fun productosDao(): ProductosDao

    companion object {
        @Volatile
        private var INSTANCE: BodeAppDatabase? = null

        fun getDatabase(context: Context): BodeAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BodeAppDatabase::class.java,
                    "bodeapp_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
