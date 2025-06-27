# 🚗 Advanced Mileage Tracker App

An advanced mileage tracking Android application built using **Jetpack Compose** and modern Android development practices. The app enables users to **start, pause, resume, and stop** journey tracking with accurate location updates, robust background support, and persistent data storage.

---

## 📽️ Demo

<p float="left">
  <img src="https://github.com/user-attachments/assets/e1d0e84a-4aa9-4908-966f-cadc56a5ef37" height="400"/>
  <img src="https://github.com/user-attachments/assets/8fcd0062-b7a9-4c05-a5d7-edd290a45492" height="400"/>
  <img src="https://github.com/user-attachments/assets/faee9544-d123-4247-b3c1-7d2ae5b9e93e" height="400"/>
  <img src="https://github.com/user-attachments/assets/cc56e998-efd2-4cce-931f-e1b4fbc39aae" height="400"/>
</p>

<p float="left">
    <img src="https://github.com/user-attachments/assets/753fae9b-4a79-4aa5-99ea-f69779233bdb" height="400"/>
  <img src="https://github.com/user-attachments/assets/a00062c6-96fb-4132-b2f1-7bfab5da69e7" height="400"/>
  <img src="https://github.com/user-attachments/assets/2a594522-8801-4991-a305-8caaf975fdf5" height="400"/>
</p>


🎥 **[Watch Demo Video 1](https://drive.google.com/file/d/1OlcAuDqhTPjvUJLi11BWVyBwpCj59bvu/view?usp=sharing)**
🎥 **[Watch Demo Video 2](https://drive.google.com/file/d/1llNammiISlVhqPAya7zpm1AKqQjTHXEU/view?usp=sharing)**

## 📲 App Download

📦 **[Download APK](https://drive.google.com/file/d/1Ri2XKavztvLGvn9qn7ilLn_WQX_2iFQ7/view?usp=sharing)**

---

## 🧩 Features

- ✅ **Start, Stop, Pause, and Resume Journey**
- 📍 **Foreground service** using `FusedLocationProviderClient` for high-accuracy location updates
- 📉 **Distance Calculation** using Haversine formula 
- 🗺️ **Map Route Preview** (Google Maps polyline)
- ♻️ **BootReceiver** to auto-restart service after reboot
- 🔒 **Permissions Managed** (Foreground & Background Location)
- 📦 **Room Database** for storing all journey data locally
- 📋 **Past Journeys List** with detailed screen
- 🧱 **MVVM Architecture**
- 🧭 **Jetpack Navigation** with **type-safe arguments**
- 🛠️ **Dagger-Hilt** for dependency injection
- 🔄 **Coroutines + Flow** for asynchronous tasks

---

## 📲 Tech Stack

- **UI**: Jetpack Compose, Material 3
- **Architecture**: MVVM
- **Navigation**: Jetpack Navigation (Compose)
- **DI**: Dagger-Hilt
- **Location**: FusedLocationProviderClient (High Accuracy)
- **Persistence**: Room Database
- **Concurrency**: Kotlin Coroutines & Flow
- **Mapping**: Google Maps SDK
- **Others**: BootReceiver, BroadcastReceiver, Foreground Services

---

## ⚙️ Setup Instructions

1. **Clone the repo**:
   ```bash
   git clone https://github.com/madhavbhavsar/MileageTracker.git
