**README.md**

# ISEN Smart Companion

![Android CI](https://github.com/ton-username/isensmartcompanion/workflows/Android%20CI/badge.svg)

**Assistant personnel pour les étudiants de l’ISEN Toulon**, optimisant emploi du temps, vie associative et échanges avec une IA.

---

## 🚀 Description

ISEN Smart Companion est une application Android développée en Kotlin et Jetpack Compose. Elle sert d’assistant personnel aux étudiants de l’ISEN Toulon pour :
- Organiser leur planning académique et associatif  
- Découvrir et s’inscrire aux événements (BDE, Gala, cohésion…)  
- Interagir avec une IA (modèle Gemini 1.5) pour obtenir des conseils personnalisés  
- Consulter l’historique des échanges IA  
- Recevoir des notifications pour les événements épinglés  

## 🛠️ Technologies utilisées

- **Langage** : Kotlin  
- **UI** : Jetpack Compose  
- **Réseau** : Retrofit + Gson (Firebase Realtime Database)  
- **IA** : Google AI Client SDK (flash Gemini 1.5)  
- **Persistance** : Room (Base locale)  
- **Notifications** : API Android Notifications  
- **Gestion de dépendances** : Gradle  

## 📂 Architecture du projet


app/
├── src/main/java/fr/isen/tonnom/isensmartcompanion
│   ├── ui/              # Composables et navigation
│   ├── data/            # Retrofit, DAO Room, modèles
│   ├── domain/          # Entités et cas d’usage
│   └── ui/              # Écrans : Main, Events, History, EventDetail…
└── src/main/res/
├── drawable/        # Logos, icônes
└── layout/          # Layout XML restants


## 📥 Installation

1. Clone le dépôt
   
   git clone https://github.com/ton-username/isensmartcompanion.git


2. Ouvre-le dans **Android Studio**
3. Synchronise les dépendances Gradle
4. Exécute sur un émulateur ou un appareil Android (niveau API 21+ recommandé)

## 🎯 Fonctionnalités principales

1. **Accueil**

   * Saisie de questions
   * Affichage des réponses IA (flash Gemini)
2. **Événements**

   * Liste dynamique depuis Firebase
   * Détail d’événement + option « Notifier »
3. **Historique**

   * Stockage Room des échanges Q/R
   * Consultation et suppression
4. **Notifications**

   * Lancement 10 s après épinglage

## 📝 Usage

* Au premier lancement, l’app récupère la liste des événements depuis Firebase.
* Pose ta question dans l’écran d’accueil pour recevoir des conseils d’organisation.
* Dans la liste d’événements, épingle tes préférés pour recevoir une notification.
* Consulte l’onglet Historique pour retrouver tous tes échanges avec l’IA.

## 🔗 Liens utiles

* Documentation [Jetpack Compose](https://developer.android.com/jetpack/compose)
* Documentation [Retrofit](https://square.github.io/retrofit/)
* Google AI Client SDK: [https://developer.android.com/ai/google-ai-client-sdk](https://developer.android.com/ai/google-ai-client-sdk)
* Firebase Realtime Database: [https://firebase.google.com/docs/database](https://firebase.google.com/docs/database)

---

**Licence**
MIT License © 2025 Lincoln

```

- **Tagline courte (à copier dans le champ “Description” de GitHub)**  
  > Assistant personnel pour les étudiants de l’ISEN Toulon : planning, événements et IA.
