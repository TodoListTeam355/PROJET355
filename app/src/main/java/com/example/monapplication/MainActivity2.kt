package com.example.monapplication

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monapplication.composables.MonApplicationTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity2 : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MonApplicationTheme {
               SecondActivityScreen()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  SecondActivityScreen() {
    var text by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var addMoreText by remember { mutableStateOf("") }
    var categoryText by remember { mutableStateOf("") }

    var datePickerFormatter= SimpleDateFormat("dd/MM/yy", Locale.getDefault())
    val datePickerState= rememberDatePickerState()
    val showDatePickerState by remember { mutableStateOf(false
    )}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Titre au centre - Exemple avec clickable
        Text(
            text = "Nouvelle Activity",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(vertical = 32.dp)
                .clickable {
                    // Action quand on clique sur le titre
                    println("Titre cliqué!")
                }
        )

        // Zone de texte avec placeholder
        Column(modifier = Modifier.fillMaxWidth()) {
            if (text.isNotEmpty()) {
                Text(
                    text = "Écrivez votre texte",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            OutlinedTextField(
                value = text,
                onValueChange = { text = it},
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    if (text.isEmpty()) {
                        Text(text = "Écrivez votre texte ici")
                    }
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Section Calendrier - Exemple avec clickable
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Sélectionnez une date",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Container cliquable pour le calendrier
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surface,
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendrier",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))




                }

            }

//            val preferredLocales = LocaleList.forLanguageTags("fr")
//            val config = Configuration()
//            config.setLocales(preferredLocales)
//            val newContext = LocalContext.current.createConfigurationContext(config)
//            CompositionLocalProvider(
//                LocalContext provides newContext,
//                LocalConfiguration provides config,
//                LocalLayoutDirection provides LayoutDirection.Rtl,
//            ) {
//                Column(
//                    modifier = Modifier.verticalScroll(rememberScrollState()),
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                ) {
//                    // Pre-select a date for January 4, 2020
//                    // Initialize date picker with the preferred locale. Here we create a state directly,
//                    // but since the Locale was set at the CompositionLocalProvider through a Configuration,
//                    // a `val datePickerState = rememberDatePickerState(...)` will have the same effect.
//                    val datePickerState = remember {
//                        DatePickerState(
//                            initialSelectedDate = LocalDate.of(2020, 1, 4),
//                            // Set to "HE" locale.
//                            locale = preferredLocales.get(0),
//                        )
//                    }
//                    DatePicker(state = datePickerState, modifier = Modifier.padding(16.dp))
//
//                    Text(
//                        "Selected date: ${datePickerState.getSelectedDate() ?: "no selection"}",
//                        modifier = Modifier.align(Alignment.CenterHorizontally),
//                    )
//                }
//            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Section "Add More" et "Category"
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Zone Add More
            OutlinedTextField(
                value = addMoreText,
                onValueChange = { addMoreText = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add More"
                    )
                },
                placeholder = {
                    Text("Add More")
                }
            )

            // Zone Category
            OutlinedTextField(
                value = categoryText,
                onValueChange = { categoryText = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Category,
                        contentDescription = "Category"
                    )
                },
                placeholder = {
                    Text("Category")
                }
            )
        }
    }

    // Date Picker Dialog
    if (showDatePicker) {
        // Implémentation basique du DatePicker
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            title = { Text("Sélectionnez une date") },
            text = { Text("Ici vous pouvez intégrer un vrai DatePicker") },
            confirmButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Annuler")
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MonApplicationTheme {
      //  Greeting("Android")
        SecondActivityScreen()
    }
}