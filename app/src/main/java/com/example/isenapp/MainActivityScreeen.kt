package com.example.isenapp

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.google.gson.Gson


@Composable
fun MainActivityScreeen() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "main" // Assurer une valeur par défaut


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (!currentRoute.startsWith("event_details/")) {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding() // ✅ Garde le footer toujours en bas
                ) {
                    NavigationBarItem(
                        selected = currentRoute == "main",
                        onClick = { navController.navigate("main") },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
                        label = { Text("Accueil") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "events",
                        onClick = { navController.navigate("events") },
                        icon = { Icon(Icons.Default.Event, contentDescription = "Événements") },
                        label = { Text("Événements") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "history",
                        onClick = { navController.navigate("history") },
                        icon = { Icon(Icons.Default.History, contentDescription = "Historique") },
                        label = { Text("Historique") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "Agenda",
                        onClick = { navController.navigate("Agenda") },
                        icon = { Icon(Icons.Default.ViewAgenda, contentDescription = "Agenda") },
                        label = { Text("Agenda") }

                    )
                }
            }
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.ime) // ✅ Empêche le clavier de modifier la position du footer
    )

    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("main") { MainScreen() }
            composable(route = "agenda") { AgendaScreen(navController) }
            composable("history") { HistoryScreen() }
            composable("events") { EventsScreen(navController) }
            composable("event_details/{eventData}") { backStackEntry ->
                val jsonEvent = backStackEntry.arguments?.getString("eventData")
                val event = Gson().fromJson(jsonEvent, Event::class.java) // Convertir JSON → Objet Event

                EventDetailScreen(navController, event) // Passer l'objet complet
            }

        }
        }
}

