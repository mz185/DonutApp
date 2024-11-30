package com.mz.donutapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by Marinos Zinonos on 30/11/2024.
 */

@Keep
data class Frosting(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double
)
