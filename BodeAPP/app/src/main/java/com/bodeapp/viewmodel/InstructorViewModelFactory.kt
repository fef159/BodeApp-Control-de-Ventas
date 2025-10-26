package com.bodeapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bodeapp.data.db.BodeAppDatabase
import com.bodeapp.repository.ProductoRepository

class InstructorViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = BodeAppDatabase.getDatabase(context).productosDao()
        val repository = ProductoRepository(dao)
        return InstructorViewModel(repository) as T
    }
}
