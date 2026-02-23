package com.mb.pvc.presentation.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun CalculatorScreen(viewModel: ArchedWindowCalculatorViewModel = hiltViewModel()) {
    val state = viewModel.state
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Arko Hesablanması",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Text(
                    text = "PVC pəncərə ölçülərini daxil edin",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Input Section
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ModernInputField(
                        label = "Hündürlük (h)",
                        value = state.height,
                        onValueChange = { viewModel.onEvent(ArchedWindowEvent.HeightChanged(it)) },
                        imeAction = ImeAction.Next
                    )
                    ModernInputField(
                        label = "Genişlik (d)",
                        value = state.width,
                        onValueChange = { viewModel.onEvent(ArchedWindowEvent.WidthChanged(it)) },
                        imeAction = ImeAction.Next
                    )
                    ModernInputField(
                        label = "Ayaqlar (||)",
                        value = state.foot,
                        onValueChange = { viewModel.onEvent(ArchedWindowEvent.FootChanged(it)) },
                        imeAction = ImeAction.Done,
                        onDone = { 
                            keyboardController?.hide()
                            viewModel.onEvent(ArchedWindowEvent.CalculateClicked) 
                        }
                    )
                }
            }

            // Buttons Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { 
                        keyboardController?.hide()
                        viewModel.onEvent(ArchedWindowEvent.CalculateClicked) 
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Calculate, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Hesabla", fontWeight = FontWeight.Bold)
                }

                if (state.result != null) {
                    Button(
                        onClick = { viewModel.onEvent(ArchedWindowEvent.SaveOrderClicked) },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24A148))
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Yadda saxla", fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Message Alert
            if (state.message != null) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32))
                        Spacer(Modifier.width(12.dp))
                        Text(state.message, color = Color(0xFF2E7D32), fontWeight = FontWeight.Medium)
                    }
                }
            }

            // Results Section
            if (state.isLoading) {
                Box(Modifier.fillMaxWidth().padding(20.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (state.error != null) {
                Text(state.error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(8.dp))
            } else if (state.result != null) {
                val result = state.result
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Nəticələr", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        
                        // Total Length
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = format(result.total),
                                color = Color.White,
                                style = MaterialTheme.typography.displayLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "sm (Ümumi)",
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }

                        // Price
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = format(result.price),
                                color = Color.White,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "AZN (Məbləğ)",
                                color = Color.White.copy(alpha = 0.7f),
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        }
                        
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        )
                        
                        ResultRow("Framuqa (h):", "${format(result.h)} sm")
                        ResultRow("Radius (r):", "${format(result.radius)} sm")
                        ResultRow("Qövsün uzunluğu (L):", "${format(result.arcLength)} sm")
                        ResultRow("Arka ayaqları:", "${format(result.foot)} sm")
                    }
                }
            }

            // BU HİSSƏ: Menyu üçün boşluq (Spacer)
            // Bu boşluq sayəsində ən aşağıdakı nəticələr menyunun arxasında gizli qalmır
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun ModernInputField(
    label: String, 
    value: String, 
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    onDone: (() -> Unit)? = null
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (onDone != null) onDone() }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedContainerColor = Color(0xFFF8F9FA),
                unfocusedContainerColor = Color(0xFFF8F9FA)
            )
        )
    }
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.White.copy(alpha = 0.7f))
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

fun format(number: Double): String = String.format("%.2f", number)
