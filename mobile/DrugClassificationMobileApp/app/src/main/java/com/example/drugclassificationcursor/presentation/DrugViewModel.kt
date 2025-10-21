package com.example.drugclassificationcursor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drugclassificationcursor.domain.model.DrugRequest
import com.example.drugclassificationcursor.domain.usecase.PredictDrugUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DrugViewModel @Inject constructor(
    private val predictDrug: PredictDrugUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DrugUiState())
    val uiState: StateFlow<DrugUiState> = _uiState

    fun onAgeChange(value: String) { _uiState.update { it.copy(age = value) } }
    fun onNaToKChange(value: String) { _uiState.update { it.copy(naToK = value) } }
    fun onSexMChange(value: Boolean) { _uiState.update { it.copy(sexM = value) } }
    fun onBpChange(value: BpOption) { _uiState.update { it.copy(bp = value) } }
    fun onCholChange(value: CholOption) { _uiState.update { it.copy(cholesterol = value) } }

    fun predict() {
        val ageInt = _uiState.value.age.toIntOrNull()
        val naToKDouble = _uiState.value.naToK.toDoubleOrNull()
        if (ageInt == null || naToKDouble == null) {
            _uiState.update { it.copy(errorMessage = "Lütfen geçerli Age ve Na_to_K girin") }
            return
        }

        val bpLow = _uiState.value.bp == BpOption.LOW
        val bpNormal = _uiState.value.bp == BpOption.NORMAL
        val cholNormal = _uiState.value.cholesterol == CholOption.NORMAL

        val request = DrugRequest(
            Age = ageInt,
            Na_to_K = naToKDouble,
            Sex_M = _uiState.value.sexM,
            BP_LOW = bpLow,
            BP_NORMAL = bpNormal,
            Cholesterol_NORMAL = cholNormal
        )

        _uiState.update { it.copy(isLoading = true, errorMessage = null, prediction = null) }

        viewModelScope.launch {
            val result = predictDrug(request)
            _uiState.update { state ->
                result.fold(
                    onSuccess = { state.copy(isLoading = false, prediction = it.prediction) },
                    onFailure = { state.copy(isLoading = false, errorMessage = it.message ?: "Bilinmeyen hata") }
                )
            }
        }
    }
}



