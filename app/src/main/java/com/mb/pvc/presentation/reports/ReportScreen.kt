package com.mb.pvc.presentation.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ReportScreen(viewModel: ReportViewModel = hiltViewModel()) {
    val state by viewModel.reportState.collectAsState()

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
                    text = "Hesabatlar",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Text(
                    text = "Satış statistikası və gəlir analizi",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                SectionTitle("Cari Hesabat", Icons.Default.TrendingUp)
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    ModernReportCard("Bugünkü", state.dailyTotal, state.dailyCount, Color(0xFF24A148))
                    ModernReportCard("Bu həftəlik", state.weeklyTotal, state.weeklyCount, Color(0xFF0F62FE))
                    ModernReportCard("Bu aylıq", state.monthlyTotal, state.monthlyCount, Color(0xFF8A3FFC))
                }
            }

            if (state.monthlyReports.isNotEmpty()) {
                item {
                    Spacer(Modifier.height(8.dp))
                    SectionTitle("Keçmiş Aylar", Icons.Default.History)
                }

                items(state.monthlyReports) { report ->
                    ModernPreviousMonthCard(report)
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 4.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}

@Composable
fun ModernReportCard(title: String, totalAmount: Double, count: Int, color: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Assessment, contentDescription = null, tint = color)
            }
            
            Spacer(Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
                Text(
                    text = "${String.format("%.2f", totalAmount)} AZN",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                Text(
                    text = "$count sifariş",
                    style = MaterialTheme.typography.bodySmall,
                    color = color,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ModernPreviousMonthCard(report: MonthlyReport) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${report.monthName} ${report.year}".replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF37474F)
                )
                Text(
                    text = "${report.count} sifariş",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Surface(
                color = Color(0xFFF1F3F5),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "${String.format("%.2f", report.totalAmount)} AZN",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}
