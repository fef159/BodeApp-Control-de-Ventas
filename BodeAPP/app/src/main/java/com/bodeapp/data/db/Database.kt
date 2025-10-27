package com.bodeapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bodeapp.data.dao.ComprasDao
import com.bodeapp.data.dao.ProductosDao
import com.bodeapp.data.dao.VentasDao
import com.bodeapp.data.model.Compra
import com.bodeapp.data.model.Producto
import com.bodeapp.data.model.Venta

@Database(
    entities = [Producto::class, Venta::class, Compra::class],
    version = 2,
    exportSchema = false
)
abstract class BodeAppDatabase : RoomDatabase() {
    abstract fun productosDao(): ProductosDao
    abstract fun ventasDao(): VentasDao
    abstract fun comprasDao(): ComprasDao

    companion object {
        @Volatile
        private var INSTANCE: BodeAppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Agregar columna fecha a ventas
                database.execSQL("ALTER TABLE ventas ADD COLUMN fecha TEXT NOT NULL DEFAULT ''")
                // Agregar columna fecha a compras
                database.execSQL("ALTER TABLE compras ADD COLUMN fecha TEXT NOT NULL DEFAULT ''")
            }
        }

        fun getDatabase(context: Context): BodeAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BodeAppDatabase::class.java,
                    "bodeapp_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
