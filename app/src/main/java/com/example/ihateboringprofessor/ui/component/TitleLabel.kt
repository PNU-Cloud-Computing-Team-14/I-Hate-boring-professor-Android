package com.example.ihateboringprofessor.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleLabel(name: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(start = 12.dp, top = 20.dp, end = 12.dp, bottom = 12.dp) // Add padding to the Row
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.W900,
                fontSize = 28.sp
            ),
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
