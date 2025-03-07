package com.example.isenapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(viewModel: GeminiViewModel = viewModel()) {
    val messages by viewModel.responses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState() // ✅ Observe l’état de chargement

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F5F3))
    ) {
        Image()

        // ✅ La liste des messages prend l'espace restant sans pousser la zone de saisie
        Box(
            modifier = Modifier
                .weight(1f) // ✅ Permet aux messages de prendre l’espace restant
                .fillMaxWidth()
        ) {
            Affichage(messages)
        }

        // ✅ Zone de saisie FIXE en bas de l’écran
        TextInput(viewModel, isLoading)
    }
}
@Composable
fun Affichage(messages: List<String>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        reverseLayout = false // Pour afficher les nouveaux messages en bas
    ) {
        items(messages) { message ->
            val isUserMessage = message.startsWith("Vous :") // Vérifie si c'est un message utilisateur

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start // ✅ Alignement
            ) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            color = if (isUserMessage) Color.Blue else Color(0xFF4CAF50), // ✅ Bleu pour utilisateur, Vert pour IA
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = message.removePrefix("Vous : ").removePrefix("IA : "), // ✅ Nettoie le message
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun Image() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.isen),
                contentDescription = "isen",
                modifier = Modifier.size(50.dp),
            )
            Spacer(modifier = Modifier.width(240.dp))
            Text(
                text = "ISEN",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = "HELPER AI ISEN",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Red
        )
    }
}
@Composable
fun TextInput(viewModel: GeminiViewModel, isLoading: Boolean) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White) // ✅ Assure une séparation claire avec le chat
            .imePadding() // ✅ Empêche le clavier de cacher la zone de saisie
            .navigationBarsPadding() // ✅ Évite que la barre de navigation ne cache la zone
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Écrire un message...") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Gray,
                    focusedContainerColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(10.dp))

            FloatingActionButton(
                onClick = {
                    if (text.isNotBlank() && !isLoading) {
                        viewModel.sendUserMessage(text)
                        text = ""
                    }
                },
                containerColor = if (isLoading) Color.Gray else Color.Red,
                contentColor = Color.White,
                modifier = Modifier.size(48.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.send),
                        contentDescription = "Envoyer"
                    )
                }
            }
        }
    }
}
