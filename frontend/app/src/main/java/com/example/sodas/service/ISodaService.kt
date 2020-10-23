package com.example.sodas.service

import android.content.Context
import com.example.sodas.models.Soda

interface ISodaService {
    fun getAll(context: Context, completionHandler: (response: ArrayList<Soda>?) -> Unit)

    fun getById(context: Context, sodaId: Int, completionHandler: (response: Soda?) -> Unit)

    fun deleteById(context: Context, sodaId: Int, completionHandler: () -> Unit)

    fun updateSoda(context: Context, soda: Soda, completionHandler: () -> Unit)

    fun createSoda(context: Context, soda: Soda, completionHandler: () -> Unit)
}