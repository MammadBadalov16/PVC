package com.mb.pvc.presentation.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mb.pvc.data.local.entity.OrderEntity
import com.mb.pvc.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class MonthlyReport(
    val monthName: String,
    val year: Int,
    val totalAmount: Double,
    val count: Int,
    val timestamp: Long
)

data class ReportState(
    val dailyTotal: Double = 0.0,
    val dailyCount: Int = 0,
    val weeklyTotal: Double = 0.0,
    val weeklyCount: Int = 0,
    val monthlyTotal: Double = 0.0,
    val monthlyCount: Int = 0,
    val monthlyReports: List<MonthlyReport> = emptyList()
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: OrderRepository
) : ViewModel() {

    val reportState: StateFlow<ReportState> = repository.getAllOrders()
        .combine(MutableStateFlow(Unit)) { orders, _ ->
            calculateReports(orders)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReportState())

    private fun calculateReports(orders: List<OrderEntity>): ReportState {
        val todayStart = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val thisWeekStart = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val thisMonthStart = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val dailyOrders = orders.filter { it.timestamp >= todayStart }
        val weeklyOrders = orders.filter { it.timestamp >= thisWeekStart }
        val monthlyOrders = orders.filter { it.timestamp >= thisMonthStart }

        val monthFormat = SimpleDateFormat("MMMM", Locale("az"))
        val allMonthlyReports = orders.groupBy {
            val cal = Calendar.getInstance()
            cal.timeInMillis = it.timestamp
            cal.set(Calendar.DAY_OF_MONTH, 1)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            cal.timeInMillis
        }.map { (monthStart, monthOrders) ->
            val cal = Calendar.getInstance()
            cal.timeInMillis = monthStart
            MonthlyReport(
                monthName = monthFormat.format(cal.time),
                year = cal.get(Calendar.YEAR),
                totalAmount = monthOrders.sumOf { it.price * it.quantity },
                count = monthOrders.sumOf { it.quantity },
                timestamp = monthStart
            )
        }.sortedByDescending { it.timestamp }

        return ReportState(
            dailyTotal = dailyOrders.sumOf { it.price * it.quantity },
            dailyCount = dailyOrders.sumOf { it.quantity },
            weeklyTotal = weeklyOrders.sumOf { it.price * it.quantity },
            weeklyCount = weeklyOrders.sumOf { it.quantity },
            monthlyTotal = monthlyOrders.sumOf { it.price * it.quantity },
            monthlyCount = monthlyOrders.sumOf { it.quantity },
            monthlyReports = allMonthlyReports
        )
    }
}
