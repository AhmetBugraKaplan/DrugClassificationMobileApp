package com.example.drugclassificationapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugclassificationapp.data.DrugPredictionRequest
import com.example.drugclassificationapp.network.DrugApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrugPredictionViewModel @Inject constructor(
    private val drugApiService: DrugApiService
) : ViewModel() {

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
        val currentState = _uiState.value
        
        val age = currentState.age.toIntOrNull()
        val naToK = currentState.naToK.toDoubleOrNull()
        
        if (age == null || naToK == null) {
            _uiState.value = currentState.copy(
                errorMessage = "Please enter valid age and Na_to_K values"
            )
            return
        }

        val request = DrugPredictionRequest(
            Age = age,
            Na_to_K = naToK,
            Sex_M = currentState.sexM,
            BP_LOW = currentState.bpLow,
            BP_NORMAL = currentState.bpNormal,
            Cholesterol_NORMAL = currentState.cholesterolNormal
        )

        _uiState.value = currentState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val response = drugApiService.predictDrug(request)
                _uiState.value = currentState.copy(
                    isLoading = false,
                    prediction = response.prediction,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Error: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

data class DrugPredictionUiState(
    val age: String = "",
    val naToK: String = "",
    val sexM: Boolean = false,
    val bpLow: Boolean = false,
    val bpNormal: Boolean = true,
    val cholesterolNormal: Boolean = false,
    val isLoading: Boolean = false,
    val prediction: String? = null,
    val errorMessage: String? = null
)
