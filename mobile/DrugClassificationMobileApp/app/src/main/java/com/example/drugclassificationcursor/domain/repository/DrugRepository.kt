package com.example.drugclassificationcursor.domain.repository

import com.example.drugclassificationcursor.domain.model.DrugPrediction
import com.example.drugclassificationcursor.domain.model.DrugRequest

interface DrugRepository {
    suspend fun predict(request: DrugRequest): Result<DrugPrediction>
}



