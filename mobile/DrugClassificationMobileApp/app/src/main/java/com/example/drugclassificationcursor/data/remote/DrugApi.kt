package com.example.drugclassificationcursor.data.remote

import com.example.drugclassificationcursor.data.remote.dto.DrugPredictionDto
import com.example.drugclassificationcursor.data.remote.dto.DrugRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface DrugApi {
    @POST("/predict")
    suspend fun predict(@Body body: DrugRequestDto): DrugPredictionDto
}



