package com.example.isenapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GeminiViewModel(application: Application) : AndroidViewModel(application) {
    private val apiKey = BuildConfig.AI_API_KEY
    private val generativeModel = GenerativeModel("gemini-1.5-flash", apiKey)

    private val database = AppDatabase.getDatabase(application) // ✅ Utilisation correcte de Room
    private val messageDao = database.messageDao()

    val messages = messageDao.getAllMessages() // ✅ Obtenir l'historique des messages depuis Room

    private val _responses = MutableStateFlow<List<String>>(emptyList())
    val responses: StateFlow<List<String>> = _responses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // ✅ Initialise un chat avec un historique
    private val chat = generativeModel.startChat(
        history = listOf(
            content(role = "user") { text("Hello, I have 2 dogs in my house.") },
            content(role = "model") { text("Great to meet you. What would you like to know?") }
        )
    )

    fun sendUserMessage(message: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("API_KEY_TEST", "Clé API utilisée : $apiKey")

                // Ajoute le message utilisateur à la liste
                _responses.value = _responses.value + "Vous : $message"

                // ✅ Envoi du message dans le contexte de la conversation
                val response = chat.sendMessage(message)
                response.text?.let { text ->
                    _responses.value = _responses.value + "IA : $text"

                    // ✅ Sauvegarde dans Room
                    messageDao.insertMessage(MessageEntity(userMessage = message, aiResponse = text))
                }
            } catch (e: Exception) {
                _responses.value = _responses.value + "Erreur : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMessage(message: MessageEntity) {
        viewModelScope.launch { messageDao.deleteMessage(message) }
    }

    fun deleteAllMessages() {
        viewModelScope.launch { messageDao.deleteAllMessages() }
    }
}
