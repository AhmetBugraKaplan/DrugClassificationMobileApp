package com.example.drugclassificationcursor.data.mapper

import com.example.drugclassificationcursor.data.remote.dto.DrugPredictionDto
import com.example.drugclassificationcursor.data.remote.dto.DrugRequestDto
import com.example.drugclassificationcursor.domain.model.DrugPrediction
import com.example.drugclassificationcursor.domain.model.DrugRequest

fun DrugRequest.toDto(): DrugRequestDto = DrugRequestDto(
    Age = Age,
    Na_to_K = Na_to_K,
    Sex_M = Sex_M,
    BP_LOW = BP_LOW,
    BP_NORMAL = BP_NORMAL,
    Cholesterol_NORMAL = Cholesterol_NORMAL
)

fun DrugPredictionDto.toDomain(): DrugPrediction = DrugPrediction(
    prediction = prediction
)



