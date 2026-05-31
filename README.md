# 🌙 MuslimPath

> _Your Ramadhan Companion. Beautifully Crafted._

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.10.0-4285F4?logo=jetpackcompose&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Platform](https://img.shields.io/badge/Platform-Android_%7C_iOS-0ABF53?logo=android&logoColor=white)](https://github.com/anomalyco/ramadhan)
[![License](https://img.shields.io/badge/License-CC_BY--NC--ND_4.0-E63946?logo=creativecommons&logoColor=white)](https://creativecommons.org/licenses/by-nc-nd/4.0/)

A beautifully crafted Ramadhan companion app built with **Kotlin Multiplatform** & **Compose Multiplatform**.
Read the Quran, track prayer times, find the Qibla, and organize your bookmarks — all in one place.

<p align="center">
  <img src="docs/assets/screenshots/home_main.png" alt="MuslimPath Home" width="280" />
</p>

---

## ✨ Screenshots

<table>
  <tr>
    <td align="center"><b>Beranda (Home)</b></td>
    <td align="center"><b>Doa (Prayer Schedule)</b></td>
  </tr>
  <tr>
    <td><img src="docs/assets/screenshots/home_main.png" alt="Home" width="260" /></td>
    <td><img src="docs/assets/screenshots/pray_main.png" alt="Prayer Schedule" width="260" /></td>
  </tr>
  <tr>
    <td align="center"><b>Quran Surah List</b></td>
    <td align="center"><b>Qibla Compass</b></td>
  </tr>
  <tr>
    <td><img src="docs/assets/screenshots/quran_surah_list.png" alt="Quran Surah List" width="260" /></td>
    <td><img src="docs/assets/screenshots/qibla_compass.png" alt="Qibla Compass" width="260" /></td>
  </tr>
</table>

---

## 📱 Features

### 🏠 Home

<p align="center">
  <img src="docs/assets/screenshots/home_main.png" alt="Home Dashboard" width="260" />
</p>

The Home tab greets you with **"Assalamualaikum!"** alongside your current city and date.
A prominent prayer countdown card shows the next prayer with remaining time and a mosque illustration.
Quick-access **"Surah Populer"** carousel highlights Al-Kahf, Yasin, and Al-Mulk.
**"Terakhir Dibaca"** picks up right where you left off.
A search box lets you find any ayat or surah instantly — **"Cari Ayat atau Surah..."**.

<p align="center">
  <img src="docs/assets/screenshots/home_location_picker.png" alt="Location Picker" width="260" />
</p>

The **location picker** uses a province → city hierarchy covering all of Indonesia, so prayer times and Qibla bearings are always accurate.

---

### 🕌 Prayer Schedule

<p align="center">
  <img src="docs/assets/screenshots/pray_main.png" alt="Prayer Schedule" width="260" />
</p>

The Prayer tab displays a live countdown timer to the next prayer (e.g., _01:38:32_), flanked by the previous and upcoming prayer times.
All five daily prayers — **Subuh, Dzuhur, Ashar, Maghrib, Isya** — are listed with their scheduled times and per-prayer alarm toggles.
A date picker lets you check past or future schedules at a glance.

<details>
<summary>🎬 Watch Adzan Notification Demo</summary>

<video src="docs/assets/videos/pray_time_notif.webm" autoplay loop muted playsinline width="280"></video>

</details>

---

### 📖 Quran

<table>
  <tr>
    <td align="center"><b>Surah List</b></td>
    <td align="center"><b>Surah Detail</b></td>
  </tr>
  <tr>
    <td><img src="docs/assets/screenshots/quran_surah_list.png" alt="Surah List" width="260" /></td>
    <td><img src="docs/assets/screenshots/quran_detail.png" alt="Surah Detail" width="260" /></td>
  </tr>
</table>

Browse all **114 surahs** with gold-outlined number badges, Latin names, Indonesian translations, and ayat counts.
Tap a surah to reveal per-ayat cards featuring **Arabic text in the LPMQ IsepMisbah font**, Latin transliteration, and Indonesian translation.

<table>
  <tr>
    <td align="center"><b>Ayat Actions</b></td>
    <td align="center"><b>Bookmark Picker</b></td>
  </tr>
  <tr>
    <td><img src="docs/assets/screenshots/quran_ayat_sheet.png" alt="Ayat Bottom Sheet" width="260" /></td>
    <td><img src="docs/assets/screenshots/quran_bookmark_picker.png" alt="Bookmark Picker" width="260" /></td>
  </tr>
</table>

The **ayat bottom sheet** (_Pilihan Ayat_) gives you instant access to:
- ▶️ **Play Audio** — stream recitation with a full playback bar
- 🔖 **Bookmark** — save to a playlist/category
- 🔗 **Share** — share the ayat text
- 📋 **Copy** — copy to clipboard

Saved ayats show a **"Tersimpan di bookmark"** badge.

<details>
<summary>🎬 Watch Ayat Share Demo</summary>

<video src="docs/assets/videos/quran_ayat_share.mp4" autoplay loop muted playsinline width="280"></video>

</details>

<table>
  <tr>
    <td align="center"><b>Audio Player</b></td>
    <td align="center"><b>Now Playing</b></td>
  </tr>
  <tr>
    <td><img src="docs/assets/screenshots/quran_audio_player.png" alt="Audio Player" width="260" /></td>
    <td><img src="docs/assets/screenshots/quran_audio_playing.png" alt="Audio Playing" width="260" /></td>
  </tr>
</table>

The floating audio player bar shows the **Now Playing** surah and ayat, reciter name (e.g., _Misyari Rasyid_), a progress bar, and prev/next/close controls.

<details>
<summary>🎬 Watch Audio Playback Demo</summary>

<video src="docs/assets/videos/quran_audio_playback.webm" autoplay loop muted playsinline width="280"></video>

</details>

---

### 🧭 Qibla Compass

<p align="center">
  <img src="docs/assets/screenshots/qibla_compass.png" alt="Qibla Compass" width="260" />
</p>

The Qibla tab features a **custom-drawn compass dial** with cardinal letters (U/T/S/B for Indonesian Utara/Timur/Selatan/Barat), decorative petals, 5° tick marks, and a gold Kaaba pointer.
Your current city is displayed as a chip, along with the precise bearing degree (e.g., _19°_) and cardinal direction text.

<details>
<summary>🎬 Watch Compass Rotation Demo</summary>

<video src="docs/assets/videos/qibla_compass_rotation.mp4" autoplay loop muted playsinline width="280"></video>

</details>

---

### 🔖 Bookmarks

<table>
  <tr>
    <td align="center"><b>Saved Ayats</b></td>
    <td align="center"><b>Create Category (empty)</b></td>
    </tr>
  <tr>
    <td><img src="docs/assets/screenshots/bookmark_main.png" alt="Bookmarks" width="260" /></td>
    <td><img src="docs/assets/screenshots/bookmark_create_category.png" alt="Create Category" width="260" /></td>
  </tr>
  <tr>
    <td align="center" colspan="2"><b>Create Category (filled)</b></td>
  </tr>
  <tr>
    <td align="center" colspan="2"><img src="docs/assets/screenshots/bookmark_create_category_filled.png" alt="Create Category Filled" width="260" /></td>
  </tr>
</table>

The **"Ayat Favorit"** tab organizes your saved ayats with:
- **Category chips** — "Semua" + custom playlists (e.g., "Favorit") with color-coded labels
- **Bookmark cards** — showing Arabic text, translation, surah name + number, category badge, and save timestamp
- **Full-text search** — FTS-powered instant search across all bookmarks
- **Create new playlist** — bottom sheet with name input and color palette
- **Delete** — remove individual bookmarks or entire categories

<details>
<summary>🎬 Watch Bookmark Navigation & Copy Demo</summary>

<video src="docs/assets/videos/quran_bookmark_nav_and_copy_ayat.mp4" autoplay loop muted playsinline width="280"></video>

</details>

---

### 📲 Deep Links

MuslimPath supports deep linking for direct Quran navigation:

```
ramadhancamp://quran/{surahId}           → Open surah
ramadhancamp://quran/{surahId}/{ayatNumber} → Open specific ayat
```

---

## 🎨 Design System

### Colors

| Swatch | Hex | Usage |
|:------:|:---:|:------|
| 🟫 | `#10221D` | **bgPrimary** — main background |
| 🟫 | `#1A2E28` | **bgSecondary** — card background |
| 🟩 | `#064C39` | **bgSurface** — elevated surfaces |
| 🟢 | `#4ADE80` | **accentPrimary** — highlights, active states |
| 🟡 | `#D4AF37` | **accentGold** — surah badges, Kaaba pointer |
| ⬜ | `100% white` | **textPrimary** — main text |
| ⬜ | `80% white` | **textSecondary** — secondary text |
| ⬜ | `40% white` | **textMuted** — muted/hint text |
| ⬛ | `dark text` | **textOnLight** — text on accent backgrounds |
| ⬜ | `10% white` | **divider** — separator lines |

### Typography

All text — including Quran ayat — uses **LPMQ IsepMisbah**, ensuring consistent and beautiful Arabic rendering.

| Token | Size | Weight |
|:------|:----:|:------:|
| `headlineLarge` | 24sp | Bold |
| `headlineSmall` | 17sp | SemiBold |
| `bodyLarge` | 15sp | Normal |
| `labelLarge` | 14sp | Medium |
| `labelSmall` | 12sp | Medium |

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|:-----------|:-------:|:--------|
| Kotlin | 2.3.0 | Language |
| Compose Multiplatform | 1.10.0 | Shared UI framework |
| Navigation3 | 1.0.0-alpha05 | In-app navigation |
| Ktor | 3.4.0 | HTTP client & API calls |
| Koin | 4.1.1 | Dependency injection |
| Coil | 3.4.0 | Image loading |
| Kotlinx Serialization | 1.10.0 | JSON parsing |
| Kotlinx Coroutines | 1.10.2 | Async programming |
| AGP | 9.0.1 | Android build |
| Android minSdk / compileSdk | 27 / 36 | Android targets |
| iOS deployment target | 18.2 | iOS targets |

**API**: [eQuran.id](https://equran.id/api/) — Quran text, surah metadata, and prayer schedules.

---

## 🏗 Architecture

MuslimPath follows **Clean Architecture** with an **MVI** presentation pattern.

```
┌─────────────────────────────────────────────────┐
│                  Presentation                    │
│  Screen → ViewModel → Content (3-tier pattern)  │
│  State / Event / Effect model per feature        │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│                   Domain                        │
│  Models · Repository Interfaces · Use Cases     │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│                    Data                         │
│  Repository Impls · DTOs · Mappers · Datasources │
└─────────────────────────────────────────────────┘
```

Each feature (`home`, `pray`, `quran`, `qibla`, `bookmark`, `about`) is organized as:

```
feature/{name}/
├── presentation/
│   ├── model/          # State, Event, Effect
│   ├── components/     # Feature-specific composables
│   └── {Name}Screen.kt # 3-tier: Screen → ViewModel → Content
├── domain/
│   ├── model/          # Pure data classes
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Business logic
├── data/
│   ├── model/          # DTOs
│   ├── mapper/         # DTO → Domain mappers
│   ├── datasource/     # Remote datasource & Preferences
│   └── repositories/   # Repository implementations
└── di/
    └── {Name}Module.kt # Koin module
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- Xcode 16+ (for iOS)
- JDK 17+

### Android

```bash
# Clone the repository
git clone https://github.com/anomalyco/ramadhan.git
cd ramadhan

# Build debug APK
./gradlew :composeApp:assembleDebug

# Install on connected device
./gradlew :composeApp:installDebug
```

### iOS

1. Open `iosApp/iosApp.xcodeproj` in Xcode
2. Select a simulator or connected device
3. Press ▶️ Run

---

## 📂 Project Structure

```
ramadhan/
├── composeApp/
│   └── src/
│       ├── commonMain/kotlin/com/iqbalwork/ramadhancamp/
│       │   ├── App.kt                        # Root: Theme + Nav setup
│       │   ├── feature/
│       │   │   ├── home/
│       │   │   ├── pray/
│       │   │   ├── quran/
│       │   │   ├── qibla/
│       │   │   ├── bookmark/
│       │   │   └── about/
│       │   └── shared/
│       │       ├── common/
│       │       │   ├── navigation/            # AppDestination, NavController
│       │       │   ├── network/              # Ktor HttpClient, safeApiCall
│       │       │   ├── preferences/          # AppPreferences, ScopedPreferences
│       │       │   ├── ui/                   # BaseViewModel, Theme, components
│       │       │   ├── geo/                   # Compass & location (expect/actual)
│       │       │   └── utils/                # AppError, platform helpers
│       │       └── di/
│       │           └── initKoin.kt            # Koin module registry
│       ├── androidMain/kotlin/...             # Android-specific implementations
│       └── nativeMain/kotlin/...             # iOS/Native-specific implementations
├── iosApp/                                    # iOS app entry point
├── docs/
│   └── assets/
│       ├── screenshots/
│       └── videos/
├── gradle/
│   └── libs.versions.toml                     # Version catalog
└── build.gradle.kts
```

---

## 🙏 Acknowledgments

- **[eQuran.id](https://equran.id/)** — Free Quran API providing surah data, ayat translations, and prayer schedules
- **LPMQ IsepMisbah** — Beautiful Arabic font for Quran text rendering
- **[Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)** — Shared logic across platforms
- **[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)** — Shared UI across platforms

---

## 📄 License

This project is licensed under **Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International (CC BY-NC-ND 4.0)**.

© 2026 Alexius Andrianno Alfa Renanta. All rights reserved.

[![CC BY-NC-ND 4.0](https://licensebuttons.net/l/by-nc-nd/4.0/88x31.png)](https://creativecommons.org/licenses/by-nc-nd/4.0/)