package com.mz.donutapp.data.repo

import android.content.Context
import com.google.gson.Gson
import com.mz.donutapp.domain.enum.OptionsType

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

interface DonutRepository {
    suspend fun <T> getOptions(type: OptionsType): Result<List<T>>
}

class DonutRepositoryImpl(
    private val context: Context
) : DonutRepository {

    override suspend fun <T> getOptions(type: OptionsType): Result<List<T>> {
        return runCatching {
            val jsonString = readJsonFromAssets(type.fileName)
            Gson().fromJson(jsonString, type.type)
        }
    }

    private fun readJsonFromAssets(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}