package com.example.drugclassificationapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drugclassificationapp.ui.theme.DrugClassificationAppTheme
import com.example.drugclassificationapp.viewmodel.DrugPredictionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrugClassificationAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DrugPredictionScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrugPredictionScreen(
    modifier: Modifier = Modifier,
    viewModel: DrugPredictionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "İlaç Sınıflandırma Uygulaması",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Age input
        OutlinedTextField(
            value = uiState.age,
            onValueChange = viewModel::updateAge,
            label = { Text("Yaş (Age)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true
        )
        
        // Na_to_K input
        OutlinedTextField(
            value = uiState.naToK,
            onValueChange = viewModel::updateNaToK,
            label = { Text("Na_to_K") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true
        )
        
        // Sex_M checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.sexM,
                onCheckedChange = viewModel::updateSexM
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Erkek (Sex_M)")
        }
        
        // BP_LOW checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.bpLow,
                onCheckedChange = viewModel::updateBpLow
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Düşük Tansiyon (BP_LOW)")
        }
        
        // BP_NORMAL checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.bpNormal,
                onCheckedChange = viewModel::updateBpNormal
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Normal Tansiyon (BP_NORMAL)")
        }
        
        // Cholesterol_NORMAL checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.cholesterolNormal,
                onCheckedChange = viewModel::updateCholesterolNormal
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Normal Kolesterol (Cholesterol_NORMAL)")
        }
        
        // Predict button
        Button(
            onClick = viewModel::predictDrug,
            enabled = !uiState.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text("Tahmin Et")
        }
        
        // Error message
        uiState.errorMessage?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        
        // Prediction result
        uiState.prediction?.let { prediction ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tahmin Sonucu:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = prediction,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    // Drug image based on prediction
                    when (prediction.lowercase()) {
                        "drugy" -> {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "💊",
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                        "drugx" -> {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "💉",
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                        "druga" -> {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "🩺",
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                        "drugc" -> {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "🧬",
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                        "drugb" -> {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "⚕️",
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    }
                }
            }
        }
    }
}