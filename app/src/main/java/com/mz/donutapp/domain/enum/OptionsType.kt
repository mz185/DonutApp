package com.mz.donutapp.domain.enum

import com.google.gson.reflect.TypeToken
import com.mz.donutapp.data.model.Filling
import com.mz.donutapp.data.model.Frosting
import java.lang.reflect.Type

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

enum class OptionsType(val fileName: String) {
    FROSTING("frost.json"),
    FILLING("fill.json");

    val type: Type
        get() {
            return when (this) {
                FROSTING -> object : TypeToken<List<Frosting>>() {}.type
                FILLING -> object : TypeToken<List<Filling>>() {}.type
            }
        }
}