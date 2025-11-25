package com.example.monapplication.composables

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monapplication.MainActivity2
import org.intellij.lang.annotations.JdkConstants

import androidx.compose.ui.platform.LocalContext



@Composable
fun MyBottomAppBar(
    context: Context = LocalContext.current
) {

    Button(onClick = {

        val intent = Intent(context, MainActivity2::class.java)
        context.startActivity(intent)
    },
        modifier = Modifier.background(androidx.compose.ui.graphics.Color(0, 122, 232))
            .clip(RoundedCornerShape(99.dp))
        ) {

//        OutlinedIconToggleButton(
//            enabled = true,
//            checked = true,
//            modifier = Modifier.size(20.dp).fillMaxWidth()
//                .background(androidx.compose.ui.graphics.Color(0, 162, 232), shape = CircleShape),
//            onCheckedChange = { }
//
//
//        )
//        {
            Text("")
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = "click",
                tint = White,


                modifier = Modifier.background(androidx.compose.ui.graphics.Color(0, 162, 232))
                    .clip(RoundedCornerShape(39.dp)).size(23.dp,34.dp)

            )
        }
    }



@Preview(showBackground = true)
@Composable
private fun MyBottomAppBarPreview () {
    MyBottomAppBar()
}