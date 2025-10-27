package com.bodeapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bodeapp.data.db.BodeAppDatabase
import com.bodeapp.repository.ComprasRepository
import com.bodeapp.repository.ProductoRepository
import com.bodeapp.repository.VentasRepository

class InstructorViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = BodeAppDatabase.getDatabase(context)
        val productoRepo = ProductoRepository(db.productosDao())
        val ventasRepo = VentasRepository(db.ventasDao())
        val comprasRepo = ComprasRepository(db.comprasDao())
        return InstructorViewModel(productoRepo, ventasRepo, comprasRepo) as T
    }
}
