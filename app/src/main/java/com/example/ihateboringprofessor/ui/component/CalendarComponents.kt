package com.example.ihateboringprofessor.ui.component

import com.example.ihateboringprofessor.viewmodel.MainViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCalendar(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onSelectedDate: (LocalDate) -> Unit = {}
) {
    val current = Calendar.getInstance()
    val currentMonth = current.get(Calendar.MONTH) + 1
    val currentYear = current.get(Calendar.YEAR)
    var currentYearMonth by remember { mutableStateOf(Pair(currentYear, currentMonth)) }
    var currentPage by remember { mutableIntStateOf(0) }
    val currentSelectedDate by viewModel.selectedDate.collectAsState()
    val pageCount = 120 // 보여줄 년수 범위: 10년
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })

    LaunchedEffect(pagerState.currentPage) {
        val addMonth = (pagerState.currentPage - currentPage)
        val newMonth = currentMonth + addMonth
        val newYear = currentYear + (newMonth - 1) / 12
        currentYearMonth = Pair(newYear, (newMonth - 1) % 12 + 1)
        currentPage = pagerState.currentPage
    }

    Column(modifier = modifier) {
        val headerText = "${currentYearMonth.first}년 ${currentYearMonth.second}월"

        CalendarHeader(
            modifier = Modifier.padding(start = 20.dp, top = 0.dp, end = 16.dp, bottom = 16.dp),
            text = headerText
        )
        HorizontalPager(
            state = pagerState
        ) { page ->
            val pageDate = LocalDate.of(
                currentYearMonth.first + (page / 12),
                (page % 12) + 1,
                1
            )
            if (page in pagerState.currentPage - 1..pagerState.currentPage + 1) {
                CalendarMonthItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    currentDate = pageDate,
                    selectedDate = currentSelectedDate,
                    onSelectedDate = { selectedDate ->
                        viewModel.selectDate(selectedDate)
                    }
                )
            }
        }
    }
}

@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(modifier = modifier) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun CalendarMonthItem(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit
) {
    val lastDay by remember { mutableIntStateOf(currentDate.lengthOfMonth()) }
    val firstDayOfWeek by remember { mutableIntStateOf(currentDate.withDayOfMonth(1).dayOfWeek.value) }
    val days by remember { mutableStateOf(IntRange(1, lastDay).toList()) }

    Column(modifier = modifier) {
        DayOfWeek()
        LazyVerticalGrid(
            modifier = Modifier.height(260.dp),
            columns = GridCells.Fixed(7)
        ) {
            for (i in 1 until firstDayOfWeek) {
                item {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(top = 10.dp)
                    )
                }
            }
            items(days) { day ->
                val dayDate = currentDate.withDayOfMonth(day)
                val isSelected = remember(selectedDate) {
                    selectedDate.compareTo(dayDate) == 0
                }
                CalendarDay(
                    modifier = Modifier.padding(top = 10.dp),
                    date = dayDate,
                    isSelected = isSelected,
                    onSelectedDate = onSelectedDate
                )
            }
        }
    }
}

@Composable
fun CalendarDay(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean,
    onSelectedDate: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    val hasEvent = false
    Column(
        modifier = modifier
            .wrapContentSize()
            .size(30.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(
                when {
                    date == today -> Color.Red
                    isSelected -> Color.Black
                    else -> Color.LightGray
                }
            )
            .clickable { onSelectedDate(date) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val textColor = if (isSelected) Color.White else Color.Black
        Text(
            textAlign = TextAlign.Center,
            text = date.dayOfMonth.toString(),
            fontSize = 12.sp,
            color = textColor
        )
        if (hasEvent) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .background(if (isSelected) Color.White else Color.Black)
            )
        }
    }
}

@Composable
fun DayOfWeek(
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = dayOfWeek,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }
    }
}
