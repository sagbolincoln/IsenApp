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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState


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

            composable("history") { HistoryScreen() }
            composable("events") { EventsScreen(navController) }
            composable("event_details/{eventTitle}") { backStackEntry ->
                val eventTitle = backStackEntry.arguments?.getString("eventTitle") ?: "Titre inconnu"
                Log.d("DEBUG", "🆔 Titre reçu : $eventTitle")
                // Afficher le titre et la description sur l'écran de détails
                EventDetailScreen(navController, eventTitle)

            }


        }
        }
}
