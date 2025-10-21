package com.example.drugclassificationcursor.domain.usecase

import com.example.drugclassificationcursor.domain.model.DrugPrediction
import com.example.drugclassificationcursor.domain.model.DrugRequest
import com.example.drugclassificationcursor.domain.repository.DrugRepository
import javax.inject.Inject

class PredictDrugUseCase @Inject constructor(
    private val repository: DrugRepository
) {
    suspend operator fun invoke(request: DrugRequest): Result<DrugPrediction> {
        return repository.predict(request)
    }
}
