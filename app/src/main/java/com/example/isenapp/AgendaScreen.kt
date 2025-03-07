package com.example.isenapp

import android.widget.CalendarView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.util.*

@Composable
fun AgendaScreen(
    navController: NavController,
    viewModel: EventViewModel = viewModel()
) {
    var selectedDate by remember { mutableStateOf("") }
    val events by viewModel.events.observeAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchEvent()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Agenda", style = MaterialTheme.typography.headlineMedium)

        AndroidView(factory = { CalendarView(it) }, update = { calendarView ->
            calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val moisEnTexte = arrayOf(
                    "janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"
                )
                selectedDate = "$dayOfMonth ${moisEnTexte[month]} $year"
                showDialog = true
            }
        })

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Événements du $selectedDate", style = MaterialTheme.typography.bodyLarge)
        val filteredEvents = events.filter { it.date == selectedDate }
        if (filteredEvents.isEmpty()) {
            Text("Aucun événement prévu pour cette date", color = Color.Gray)
        } else {
            LazyColumn {
                itemsIndexed(events.chunked(3)) { _, eventGroup ->
                    Column {
                        eventGroup.forEach { event ->
                            EventItem(
                                event = event,
                                onClick = {
                                    navController.navigate("event_details/${event.title}")
                                },
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp)) // Espacement entre chaque groupe de 3
                    }
                }
            }


        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Tous les événements", style = MaterialTheme.typography.bodyLarge)
        if (events.isEmpty()) {
            Text("Aucun événement disponible", color = Color.Gray)
        } else {
            LazyColumn {
                items(events) { event ->
                    EventItem(event = event, onClick = {
                        navController.navigate("event_details/${event.title}")
                    })
                }
            }
        }
    }

    if (showDialog) {
        AddCourseDialog(
            selectedDate = selectedDate,
            onDismiss = { showDialog = false },
            onAddCourse = { title, time, description, lieu, categorie ->
                viewModel.addEvent(
                    Event(
                        id = UUID.randomUUID().toString(),
                        title = title,
                        description = "$time - $description",
                        date = selectedDate,
                        location = lieu?.takeIf { it.isNotBlank() },
                        category = categorie?.takeIf { it.isNotBlank() }

                    )
                )
                showDialog = false
            }
        )
    }
}

@Composable
fun AddCourseDialog(
    selectedDate: String,
    onDismiss: () -> Unit,
    onAddCourse: (String, String, String, String?, String?) -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var time by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var lieu by remember { mutableStateOf(TextFieldValue("")) }
    var categorie by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Ajouter un cours le $selectedDate") },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Titre du cours") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Heure (ex: 10h-12h)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = lieu, onValueChange = { lieu = it }, label = { Text("Lieu (facultatif)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = categorie, onValueChange = { categorie = it }, label = { Text("Catégorie (facultatif)") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (title.text.isNotBlank() && time.text.isNotBlank()) {
                    onAddCourse(title.text, time.text, description.text, lieu.text, categorie.text)
                }
            }) {
                Text("Ajouter")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
