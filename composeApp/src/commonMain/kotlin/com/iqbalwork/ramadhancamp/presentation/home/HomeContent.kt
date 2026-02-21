package com.iqbalwork.ramadhancamp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * iqbalfauzi
 * Email: work.iqbalfauzi@gmail.com
 * Github: https://github.com/iqbalwork
 */
@Composable
fun HomeContent() {
    Scaffold(
        modifier = Modifier.background(color = Color.LightGray)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HeaderItem(modifier = Modifier.fillMaxWidth(), location = "Bandung, Jawa Barat")
            Spacer(modifier = Modifier.height(24.dp))
            Card {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(text = "Berikutnya", fontSize = 14.sp)
                            Text(text = "Maghrib", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(text = "18:20", fontSize = 16.sp)
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(text = "Sisa Waktu", fontSize = 14.sp)
                            Text(text = "45 Menit", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(40.dp)
                        ).padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = Color.Blue,
                        )
                        Text(text = "Selasa, 24 Sya'ban 1447H", fontSize = 12.sp)
                    }
                }
            }
        }

    }
}

@Composable
fun HeaderItem(modifier: Modifier = Modifier, location: String) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.Blue
            )
            Text(text = location)
        }

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            modifier = Modifier
                .background(color = Color.White, shape = CircleShape)
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewHomeContent() {
    MaterialTheme {
        HomeContent()
    }
}
