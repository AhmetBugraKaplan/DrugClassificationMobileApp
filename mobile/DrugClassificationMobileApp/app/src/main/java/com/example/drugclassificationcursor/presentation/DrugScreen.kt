package com.example.drugclassificationcursor.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrugScreen(viewModel: DrugViewModel = hiltViewModel()) {
    val ui = viewModel.uiState.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Drug Prediction") }) }) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Card(colors = CardDefaults.cardColors()) {
                Column(Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = ui.value.age,
                        onValueChange = viewModel::onAgeChange,
                        label = { Text("Age") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = ui.value.naToK,
                        onValueChange = viewModel::onNaToKChange,
                        label = { Text("Na_to_K") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )

                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Sex_M")
                        Spacer(Modifier.weight(1f))
                        Switch(checked = ui.value.sexM, onCheckedChange = viewModel::onSexMChange)
                    }

                    Spacer(Modifier.height(8.dp))
                    var bpExpanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(expanded = bpExpanded, onExpandedChange = { bpExpanded = !bpExpanded }) {
                        OutlinedTextField(
                            value = ui.value.bp.name,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("BP") },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = bpExpanded) },
                            colors = OutlinedTextFieldDefaults.colors()
                        )
                        ExposedDropdownMenu(expanded = bpExpanded, onDismissRequest = { bpExpanded = false }) {
                            DropdownMenuItem(text = { Text("HIGH") }, onClick = { viewModel.onBpChange(BpOption.HIGH); bpExpanded = false })
                            DropdownMenuItem(text = { Text("LOW") }, onClick = { viewModel.onBpChange(BpOption.LOW); bpExpanded = false })
                            DropdownMenuItem(text = { Text("NORMAL") }, onClick = { viewModel.onBpChange(BpOption.NORMAL); bpExpanded = false })
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                    var cholExpanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(expanded = cholExpanded, onExpandedChange = { cholExpanded = !cholExpanded }) {
                        OutlinedTextField(
                            value = ui.value.cholesterol.name,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Cholesterol") },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cholExpanded) }
                        )
                        ExposedDropdownMenu(expanded = cholExpanded, onDismissRequest = { cholExpanded = false }) {
                            DropdownMenuItem(text = { Text("HIGH") }, onClick = { viewModel.onCholChange(CholOption.HIGH); cholExpanded = false })
                            DropdownMenuItem(text = { Text("NORMAL") }, onClick = { viewModel.onCholChange(CholOption.NORMAL); cholExpanded = false })
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Button(onClick = { viewModel.predict() }, enabled = !ui.value.isLoading) {
                Text("Tahmin Et")
            }

            Spacer(Modifier.height(16.dp))
            if (ui.value.isLoading) {
                CircularProgressIndicator()
            }

            ui.value.prediction?.let { pred ->
                Spacer(Modifier.height(12.dp))
                Card(colors = CardDefaults.cardColors()) {
                    Column(Modifier.padding(16.dp)) {
                        val res = when (pred.lowercase()) {
                            "druga" -> painterResource(id = com.example.drugclassificationcursor.R.drawable.ic_drug_a)
                            "drugb" -> painterResource(id = com.example.drugclassificationcursor.R.drawable.ic_drug_b)
                            "drugc" -> painterResource(id = com.example.drugclassificationcursor.R.drawable.ic_drug_c)
                            "drugx" -> painterResource(id = com.example.drugclassificationcursor.R.drawable.ic_drug_x)
                            "drugy" -> painterResource(id = com.example.drugclassificationcursor.R.drawable.ic_drug_y)
                            else -> painterResource(id = com.example.drugclassificationcursor.R.drawable.ic_drug_y)
                        }
                        Image(painter = res, contentDescription = "Prediction Image")
                        Spacer(Modifier.height(8.dp))
                        Text(text = "Tahmin: $pred", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            ui.value.errorMessage?.let { Text(text = it) }
        }
    }
}

