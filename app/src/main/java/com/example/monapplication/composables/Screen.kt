package com.example.monapplication.composables

import android.R
import android.graphics.Color
import android.health.connect.datatypes.ExerciseCompletionGoal
import android.text.style.BackgroundColorSpan
import android.view.Menu
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



@Composable
fun Screen(modifier: Modifier = Modifier) {
    Scaffold (
        topBar = {
            MyTopAppBar()
        },
        bottomBar={
            MyBottomAppBar()



        }

    ){
        Column(modifier= Modifier.padding(it)){
            Menu()
        }
    }





}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Screen()

}