# Tab Nav Demo — Design

**Date:** 2026-02-22
**Status:** Approved

---

## Goal

Refactor `MainScreen.kt` entry registration so each tab feature owns its entries in a
separate file. Add full demo screens per tab (3 levels deep) with per-tab bottom sheets.
Every action on `AppNavigationController` must be reachable from the UI.

---

## Approach

**Approach A — `EntryProviderBuilder` extension functions.**
Each tab feature exposes `fun EntryProviderBuilder<NavKey>.<tab>TabEntries(nav: AppNavigationController)`
in a `[Tab]NavEntries.kt` file at the feature root.
`MainScreen.kt` dispatches via `when(tab)` — zero extra abstractions, pure Nav3 DSL.

---

## Section 1 — Destination changes (`AppDestination.kt`)

### New `TabDestination` entries
```
HomeSubDetail                     (new)
PrayDetail, PraySubDetail         (new)
QuranDetail, QuranSubDetail       (new)
QiblaDetail, QiblaSubDetail       (new)
BookmarkDetail, BookmarkSubDetail (new)
```

### New `DialogDestination` entries (per-tab sheets)
```
HomeSheet     ← replaces SampleDialog
PraySheet
QuranSheet
QiblaSheet
BookmarkSheet
```

`SampleDialog` is removed.
`appSavedStateConfig` updated with all new subclasses.

---

## Section 2 — Navigation action coverage

### HomeMain (root-level + tab-level + cross-tab)
| Button | Action |
|---|---|
| Push Detail | `navigateToInsideTab(HomeDetail)` |
| Replace with Detail | `navigateToInsideTab(HomeDetail, withReplace=true)` |
| Navigate to Auth | `navigateTo(RootDestination.Auth)` |
| Navigate to Auth (replace) | `navigateTo(RootDestination.Auth, withReplace=true)` |
| Start New Flow → Auth | `startNewFlow(RootDestination.Auth)` |
| Switch to Pray | `switchTab(AppTab.Pray)` |
| Show Home Sheet | `showDialog(DialogDestination.HomeSheet)` |
| — | `subscribeToResult` (displays result received from SubDetail) |

### AuthScreen (demo return root navigation)
| Button | Action |
|---|---|
| Navigate to Main (push) | `navigateTo(RootDestination.Main)` |
| Navigate to Main (replace) | `navigateTo(RootDestination.Main, withReplace=true)` |
| Start New Flow → Main | `startNewFlow(RootDestination.Main)` |

### [Tab]Main — all non-Home tabs (Pray / Quran / Qibla / Bookmark)
| Button | Action |
|---|---|
| Push Detail | `navigateToInsideTab([Tab]Detail)` |
| Replace with Detail | `navigateToInsideTab([Tab]Detail, withReplace=true)` |
| Switch to [Next Tab] | `switchTab(AppTab.[Next])` (circular: Pray→Quran→Qibla→Bookmark→Home) |
| Show [Tab] Sheet | `showDialog(DialogDestination.[Tab]Sheet)` |
| — | `subscribeToResult` (displays result received from SubDetail) |

### [Tab]Detail — all 5 tabs
| Button | Action |
|---|---|
| Push SubDetail | `navigateToInsideTab([Tab]SubDetail)` |
| Back | `back()` |
| Back with Result | `back(NavigationResult.Success(...))` |

### [Tab]SubDetail — all 5 tabs
| Button | Action |
|---|---|
| Back to Main | `backToScreen([Tab]Main)` |
| Back to Main with Result | `backToScreen([Tab]Main, NavigationResult.Success(...))` |
| Back one step | `back()` |

### [Tab]SheetScreen — all 5 tabs
| Button | Action |
|---|---|
| Close | `hideDialog()` |

---

## Section 3 — File structure

```
feature/
├── home/
│   ├── di/HomeModule.kt                  (existing — register new VMs if needed)
│   ├── presentation/
│   │   ├── HomeViewModel.kt              (extend: SubDetail nav, root actions, result sub)
│   │   ├── HomeMainScreen.kt             (refactor: full demo buttons)
│   │   ├── HomeDetailScreen.kt           (refactor: push SubDetail + back with result)
│   │   ├── HomeSubDetailScreen.kt        (NEW)
│   │   └── HomeSheetScreen.kt            (NEW — replaces SampleDialogScreen.kt)
│   └── HomeNavEntries.kt                 (NEW)
│
├── pray/
│   ├── di/PrayModule.kt                  (NEW)
│   ├── presentation/
│   │   ├── PrayViewModel.kt              (NEW)
│   │   ├── PrayMainScreen.kt             (NEW)
│   │   ├── PrayDetailScreen.kt           (NEW)
│   │   ├── PraySubDetailScreen.kt        (NEW)
│   │   └── PraySheetScreen.kt            (NEW)
│   └── PrayNavEntries.kt                 (NEW)
│
├── quran/  (same structure as pray)
├── qibla/  (same structure as pray)
├── bookmark/ (same structure as pray)
│
├── auth/
│   └── presentation/
│       └── AuthScreen.kt                 (NEW — no ViewModel, injects navHolder directly)
│
└── main/
    └── MainScreen.kt                     (modify: dispatch to *TabEntries extension fns)

shared/common/navigation/
└── AppDestination.kt                     (modify: new destinations + savedStateConfig)

initKoin.kt                               (modify: add prayModule, quranModule, qiblaModule, bookmarkModule)
```

---

## Section 4 — Per-feature ViewModel pattern

Each tab ViewModel follows the same pattern as `HomeViewModel`:
- Injects `AppNavigationControllerHolder` via Koin constructor injection
- Exposes one `fun` per navigation action needed by that tab's screens
- AuthScreen skips ViewModel — calls `navHolder.get()` directly via `koinInject()`

Result key convention: `"[tab]_subdetail_result"` (e.g. `"home_subdetail_result"`)
Result data: a simple `data class [Tab]SubDetailResult(val message: String) : NavigationResultData`

---

## Section 5 — `MainScreen.kt` after refactor

```kotlin
entryProvider = entryProvider {
    when (tab) {
        AppTab.Home     -> homeTabEntries(navController)
        AppTab.Pray     -> prayTabEntries(navController)
        AppTab.Quran    -> quranTabEntries(navController)
        AppTab.Qibla    -> qiblaTabEntries(navController)
        AppTab.Bookmark -> bookmarkTabEntries(navController)
    }
}
```

`PlaceholderTabScreen` is deleted. `SampleDialogScreen.kt` is deleted.

---

## Out of scope

- Real feature UI (prayer times, Quran reader, etc.) — screens are demo-only
- Deep link support
- Animated tab transitions per-tab
