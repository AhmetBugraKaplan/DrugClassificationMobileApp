package com.example.drugclassificationcursor.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrugRequestDto(
    val Age: Int,
    val Na_to_K: Double,
    val Sex_M: Boolean,
    val BP_LOW: Boolean,
    val BP_NORMAL: Boolean,
    val Cholesterol_NORMAL: Boolean
)

@JsonClass(generateAdapter = true)
data class DrugPredictionDto(
    val prediction: String
)



