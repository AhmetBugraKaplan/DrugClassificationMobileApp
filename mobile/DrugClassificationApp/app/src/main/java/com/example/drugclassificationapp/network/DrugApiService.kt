package com.example.drugclassificationapp.network

import com.example.drugclassificationapp.data.DrugPredictionRequest
import com.example.drugclassificationapp.data.DrugPredictionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DrugApiService {
    @POST("/predict")
    suspend fun predictDrug(@Body request: DrugPredictionRequest): DrugPredictionResponse
}
