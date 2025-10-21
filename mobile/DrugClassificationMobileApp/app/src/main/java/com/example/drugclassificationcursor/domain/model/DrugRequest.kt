package com.example.drugclassificationcursor.domain.model

data class DrugRequest(
    val Age: Int,
    val Na_to_K: Double,
    val Sex_M: Boolean,
    val BP_LOW: Boolean,
    val BP_NORMAL: Boolean,
    val Cholesterol_NORMAL: Boolean
)



