package com.example.drugclassificationapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugclassificationapp.data.DrugPredictionRequest
import com.example.drugclassificationapp.network.DrugApiService
import com.example.drugclassificationapp.network.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DrugPredictionViewModel : ViewModel() {
    private val apiService: DrugApiService = NetworkModule.drugApiService
    
    private val _uiState = MutableStateFlow(DrugPredictionUiState())
    val uiState: StateFlow<DrugPredictionUiState> = _uiState.asStateFlow()
    
    fun updateAge(age: String) {
        _uiState.value = _uiState.value.copy(age = age)
    }
    
    fun updateNaToK(naToK: String) {
        _uiState.value = _uiState.value.copy(naToK = naToK)
    }
    
    fun updateSexM(sexM: Boolean) {
        _uiState.value = _uiState.value.copy(sexM = sexM)
    }
    
    fun updateBpLow(bpLow: Boolean) {
        _uiState.value = _uiState.value.copy(bpLow = bpLow)
    }
    
    fun updateBpNormal(bpNormal: Boolean) {
        _uiState.value = _uiState.value.copy(bpNormal = bpNormal)
    }
    
    fun updateCholesterolNormal(cholesterolNormal: Boolean) {
        _uiState.value = _uiState.value.copy(cholesterolNormal = cholesterolNormal)
    }
    
    fun predictDrug() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val age = _uiState.value.age.toIntOrNull() ?: 0
                val naToK = _uiState.value.naToK.toDoubleOrNull() ?: 0.0
                
                val request = DrugPredictionRequest(
                    Age = age,
                    Na_to_K = naToK,
                    Sex_M = _uiState.value.sexM,
                    BP_LOW = _uiState.value.bpLow,
                    BP_NORMAL = _uiState.value.bpNormal,
                    Cholesterol_NORMAL = _uiState.value.cholesterolNormal
                )
                
                val response = apiService.predictDrug(request)
                
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        prediction = response.body()?.prediction ?: "",
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "API Hatası: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Bağlantı hatası: ${e.message}"
                )
            }
        }
    }
}

data class DrugPredictionUiState(
    val age: String = "",
    val naToK: String = "",
    val sexM: Boolean = false,
    val bpLow: Boolean = false,
    val bpNormal: Boolean = false,
    val cholesterolNormal: Boolean = false,
    val isLoading: Boolean = false,
    val prediction: String = "",
    val error: String? = null
)
