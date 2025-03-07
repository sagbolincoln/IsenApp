package com.example.isenapp

import android.util.Log


class EventRepository {
    suspend fun getEvents(): List<Event>? {
        try {
            val response = RetrofitInstance.api.getEvents()
            Log.d("API_RESPONSE", "Réponse reçue : $response")

            return if (response.isSuccessful) {
                response.body().also {
                    Log.d("API_RESPONSE", "Événements récupérés : $it")
                }
            } else {
                Log.e("API_RESPONSE", "Erreur API : ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception lors de l'appel API", e)
            return null
        }
    }

}