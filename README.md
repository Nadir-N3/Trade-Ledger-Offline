# 📒 Trade‑Ledger‑Offline

![Kotlin](https://img.shields.io/badge/Kotlin-Android-7F52FF?logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-App-3DDC84?logo=android&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-KTS-02303A?logo=gradle&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-22c55e)

> **Trade‑Ledger‑Offline** is a lightweight **Android app (Kotlin)** for recording trades **fully offline**—no server, no account. Keep a personal ledger on‑device with a clean UI.

---

## ✨ Features
- Offline‑first trade ledger (date, pair/asset, side, price, qty, notes)
- List, filter/search (by asset/date)
- Simple summary totals (per asset / per period)
- Export/Import (CSV/JSON) *(optional—adapt to your implementation)*
- MIT License

---

## 🚀 Quick Start

### A) Android Studio (recommended)
1. Clone:
   ```bash
   git clone https://github.com/Nadir-N3/Trade-Ledger-Offline.git
   cd Trade-Ledger-Offline
   ```
2. Open in **Android Studio** → wait for **Gradle sync**.
3. Choose device/emulator → **Run ▶**.

### B) Gradle CLI
Build debug APK:
```bash
./gradlew assembleDebug        # (Windows: .\gradlew.bat assembleDebug)
```
Install to a connected device:
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## 🧱 Project Structure (overview)
```
Trade-Ledger-Offline/
├─ app/
│  ├─ src/main/
│  │  ├─ AndroidManifest.xml
│  │  ├─ java/...        # Kotlin sources (activities/viewmodels/etc.)
│  │  └─ res/...         # layouts, drawables, strings, themes
│  └─ build.gradle.kts
├─ build.gradle.kts
├─ settings.gradle.kts
└─ README.md
```

---

## ⚙️ Tech Notes
- **Language**: Kotlin (Android)  
- **Build**: Gradle Kotlin DSL  
- **Min/Target SDK**: see `app/build.gradle.kts`  
- **Data**: stored locally (Room/SQLite/Preferences—per your implementation)

---

## 🐞 Troubleshooting
- Gradle sync errors → update Android Gradle Plugin & SDK Platforms.
- Emulator slow → enable hardware acceleration (HAXM / Hyper‑V / HVF).
- ADB not detected → install platform‑tools or use Device Manager in Studio.

---

## 📜 License
This project is licensed under the **MIT License**. See `LICENSE`.

---

## 👤 Author
**Nadir‑N3** — [X](https://x.com/Naadiir_08) · [Instagram](https://instagram.com/__naadiir.fx)
