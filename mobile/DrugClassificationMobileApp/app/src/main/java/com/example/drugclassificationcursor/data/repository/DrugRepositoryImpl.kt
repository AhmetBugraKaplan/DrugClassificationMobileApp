package com.example.drugclassificationcursor.data.repository

import com.example.drugclassificationcursor.data.mapper.toDomain
import com.example.drugclassificationcursor.data.mapper.toDto
import com.example.drugclassificationcursor.data.remote.DrugApi
import com.example.drugclassificationcursor.domain.model.DrugPrediction
import com.example.drugclassificationcursor.domain.model.DrugRequest
import com.example.drugclassificationcursor.domain.repository.DrugRepository
import javax.inject.Inject

class DrugRepositoryImpl @Inject constructor(
    private val api: DrugApi
) : DrugRepository {
    override suspend fun predict(request: DrugRequest): Result<DrugPrediction> {
        return runCatching {
            val dto = api.predict(request.toDto())
            dto.toDomain()
        }
    }
}



