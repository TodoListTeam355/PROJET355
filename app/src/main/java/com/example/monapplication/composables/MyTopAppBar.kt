package com.example.monapplication.composables

import android.R
import android.R.attr.padding
//import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//private val ColorScheme.onprimary: Color

private val ColorScheme.onprimary: Color get() =Color(0,162,232)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {

    LargeTopAppBar(
        modifier = Modifier.padding(horizontal=6.dp),
        title = {
            Text(
                text = "Lists",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C3E50)
            )
        },

        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Menu,null)
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Settings,null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White),

//                    colors = TopAppBarDefaults.largeTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.onprimary,
//            titleContentColor = MaterialTheme.colorScheme.primary,
//            actionIconContentColor =  MaterialTheme.colorScheme.primary,
//            navigationIconContentColor =  MaterialTheme.colorScheme.primary,
//        )
    )
}