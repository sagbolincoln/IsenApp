package com.example.isenapp

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(navController: NavController, eventTitle: String?) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("event_prefs", Context.MODE_PRIVATE)
    val isNotificationEnabled = remember { mutableStateOf(sharedPreferences.getBoolean(eventTitle ?: "", false)) }

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

                        sharedPreferences.edit().putBoolean(eventTitle ?: "", newValue).apply()

                        if (newValue) {
                            scheduleNotification(context, eventTitle ?: "Événement", 10)
                        }
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
                text = eventTitle ?: "Titre introuvable",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
