package com.example.drugclassificationcursor.presentation

enum class BpOption {
    HIGH, LOW, NORMAL
}

enum class CholOption {
    HIGH, NORMAL
}

data class DrugUiState(
    val age: String = "",
    val naToK: String = "",
    val sexM: Boolean = false,
    val bp: BpOption = BpOption.HIGH,
    val cholesterol: CholOption = CholOption.HIGH,
    val isLoading: Boolean = false,
    val prediction: String? = null,
    val errorMessage: String? = null
)