# SearchableSpinner 🎯

[![JitPack](https://jitpack.io/v/AmirAlizadeh1998/SearchableSpinner.svg)](https://jitpack.io/#AmirAlizadeh1998/SearchableSpinner)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Platform](https://img.shields.io/badge/Platform-Android%20Compose-green.svg)](https://developer.android.com/jetpack/compose)

A lightweight, customizable, and modern **Searchable Spinner / Dropdown** component built with **Jetpack Compose** for Android. It allows users to easily filter and select items from simple lists or complex data models with instant search.

---

## 📸 Preview
<p align="center">
  <img src="https://github.com/user-attachments/assets/5116b830-c84f-4187-9321-6dee0a21c5b7" width="320" alt="SearchableSpinner Demo" />
</p>

---

## 🚀 Installation

### 1. Add repository

Add JitPack to your `settings.gradle.kts` (or root `build.gradle.kts`):
```kotlin
dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
google()
mavenCentral()
maven { url = uri("https://jitpack.io") }
}
}

*(If you are using Groovy `settings.gradle`:)*

groovy
dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
google()
mavenCentral()
maven { url 'https://jitpack.io' }
}
}

### 2. Add dependency

Add it to your app module's `build.gradle.kts`:

kotlin
dependencies {
implementation("com.github.AmirAlizadeh1998:SearchableSpinner:1.0.1")
}

*(Or in Groovy `build.gradle`:)*

groovy
dependencies {
implementation 'com.github.AmirAlizadeh1998:SearchableSpinner:1.0.1'
}

---

## 💡 Quick Usage

### 1. Simple List (e.g. Strings)

kotlin
val fruits = remember { listOf("Apple", "Banana", "Orange", "Mango", "Strawberry") }
var selectedFruit by remember { mutableStateOf<String?>(null) }

SearchableSpinner(
items = fruits,
selectedItem = selectedFruit,
onItemSelected = { fruit -> selectedFruit = fruit },
itemText = { it },
label = "Select a Fruit",
placeholderSearch = "Search fruit...",
isSearchable = true,
modifier = Modifier.fillMaxWidth()
)

### 2. Custom Data Models

`SearchableSpinner` supports generic types seamlessly:

kotlin
data class User(val id: Int, val name: String, val role: String)

val users = remember {
listOf(
User(1, "Amir Alizadeh", "Android Dev"),
User(2, "Sara Smith", "UI/UX Designer"),
User(3, "John Doe", "Backend Dev")
)
}
var selectedUser by remember { mutableStateOf<User?>(null) }

SearchableSpinner(
items = users,
selectedItem = selectedUser,
onItemSelected = { user -> selectedUser = user },
itemText = { user -> "${user.name} (${user.role})" },
label = "Select Team Member",
placeholderSearch = "Search members...",
isSearchable = true,
modifier = Modifier.fillMaxWidth()
)

---

## ✨ Features

- ⚡️ **Lightweight & Fast:** Zero heavy external dependencies, 100% built for Jetpack Compose.
- 🔍 **Real-time Filter:** Instant query search and quick selection.
- 🎨 **Generic & Flexible:** Works with strings, integers, or custom data classes effortlessly.
- 📱 **Material Design 3:** Perfectly adheres to modern Android Material Design standards.

---

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
