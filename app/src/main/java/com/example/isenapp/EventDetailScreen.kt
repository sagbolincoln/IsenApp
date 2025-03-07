package com.example.isenapp

import android.content.Context
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(navController: NavController, event: Event) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("event_prefs", Context.MODE_PRIVATE)

    val isNotificationEnabled = remember {
        mutableStateOf(sharedPreferences.getBoolean(event.title, false))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Détails de l'événement") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val newValue = !isNotificationEnabled.value
                        isNotificationEnabled.value = newValue
                        sharedPreferences.edit().putBoolean(event.title, newValue).apply()
                    }) {
                        Icon(
                            imageVector = if (isNotificationEnabled.value) Icons.Default.Notifications else Icons.Default.NotificationsOff,
                            contentDescription = "Activer/Désactiver la notification"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            EventDetailBox(Icons.Default.Description, "Description", event.description)
            EventDetailBox(Icons.Default.CalendarToday, "Date", event.date)
            EventDetailBox(Icons.Default.LocationOn, "Lieu", event.location ?: "Non spécifié")
            EventDetailBox(Icons.Default.Category, "Catégorie", event.category ?: "Non spécifié")
        }
    }
}

@Composable
fun EventDetailBox(icon: ImageVector, label: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(icon, contentDescription = label, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = label, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
                    Text(text = value, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
