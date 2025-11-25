package com.example.monapplication.composables

import android.R
import android.icu.text.LocaleDisplayNames
import android.provider.CalendarContract
import android.text.EmojiConsistency
import android.view.Menu
import androidx.annotation.Nullable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.sharp.AirplaneTicket
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ProductionQuantityLimits
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material.icons.sharp.BrokenImage
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.nio.file.WatchEvent
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun Menu(modifier: Modifier = Modifier) {
//    class Category(val displayName: String, val emoji: String , val color: Long) {
//
//        LISTE( displayName="liste",emoji=" ", color = ),
//        SAC(),
//        ECOUTEURS(),
//        AVION(),
//        LIVRE(),
//        MAISON(),
//    }
    val icons=listOf(

        Icons.Default.List,
        Icons.Default.Work,
        Icons.Default.Headphones,
        Icons.Default.Flight,
        Icons.Default.School,
        Icons.Default.Home,
        Icons.Default.Palette,
        Icons.Filled.ShoppingCart,


        Icons.AutoMirrored.Sharp.AirplaneTicket,
        Icons.AutoMirrored.Filled.ListAlt,
        Icons.Filled.Home,
        Icons.Filled.ShoppingCart,
        Icons.Filled.AirplanemodeActive,
        Icons.Filled.Home,
        Icons.Filled.ProductionQuantityLimits,
        Icons.Filled.Flight,
        Icons.Filled.Palette,
        Icons.Filled.Headphones,
//        Icons.Filled.WorkOutline,
//        Icons.Filled.BusinessCenter,


    )
    val couleurs=listOf(
        Color.Yellow,
        Color.Red,
        Color.Blue,
        Color.Blue,
        Color.Green,
        Color.LightGray,
        Color.Cyan,
        Color.Black,
        Color.Green,
        Color.Red,
        Color.Transparent,
    )

    LazyVerticalGrid(columns = GridCells.Adaptive(140.dp),
                        content = {
                            items(count = 11){

                                i -> val icons: ImageVector= when(i){
                                    0 -> icons[0]
                                    1 -> icons[1]
                                2 -> icons[2]
                                3 -> icons[3]
                                4 -> icons[4]
                                5 -> icons[5]
                                6 -> icons[6]
                                7 -> icons[7]
                                8 -> icons[8]
                                9 -> icons[9]
                                10 -> icons[10]
                                else -> icons[0]

                                }
                               val couleurs: Color = when(i){
                                    0 -> couleurs[0]
                                    1 -> couleurs[1]
                                    2 -> couleurs[2]
                                    3 -> couleurs[3]
                                    4 -> couleurs[4]
                                    5 -> couleurs[5]
                                    6 -> couleurs[6]
                                    7 -> couleurs[7]
                                    8 -> couleurs[8]
                                    9 -> couleurs[9]
                                    10 ->couleurs[10]
                                    else ->couleurs[0]

                                }

                                Box(modifier = Modifier
                                    .background(Color.White)
                                   // .padding(paddingValues)

                                    .padding(13.dp)
                                    .aspectRatio(0.8f)
                                   .clip(RoundedCornerShape(39.dp))
                                    .background(Color.Transparent)
                                    //.border(0.3.dp,Color.LightGray,)
                                    .shadow(300.dp,RoundedCornerShape(99.dp),false),
                                    contentAlignment = Alignment.TopStart
                                ){
                                    Column (modifier = Modifier.padding(18.dp),
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.Start) {
                                        Icon(
                                            imageVector = icons,
                                            contentDescription = "logo",
                                            tint = couleurs,
                                            modifier = Modifier.size(28.dp)

                                        )
                                        Spacer(Modifier.height(14.dp))
                                        Text(text = "Nom de l'activite $i")
                                    }

                                }
                            }
                        }
    )
}





@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Menu()

}