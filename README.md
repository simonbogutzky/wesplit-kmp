# WeSplit - Kotlin Multiplatform

A bill-splitting calculator built with Kotlin Multiplatform and Compose Multiplatform, demonstrating cross-platform architecture with shared business logic.

## Overview

WeSplit calculates how to split a restaurant bill between multiple people, including tip. This implementation showcases KMP's expect/actual pattern for platform-specific code, Compose Multiplatform's declarative UI, and proper architectural separation.

**Part of a cross-platform comparison project** - the same app implemented in SwiftUI, Kotlin Multiplatform, and Flutter.

## Tech Stack

- **Kotlin Multiplatform** - Shared business logic
- **Compose Multiplatform** - Declarative UI for iOS & Android
- **Material 3** - Modern Material Design components
- **Navigation Compose** - Type-safe navigation

## Features

- ✅ Currency input with platform-specific locale formatting
- ✅ Dynamic person count selection (2-99)
- ✅ Tip percentage picker with segmented buttons
- ✅ Real-time calculation with `derivedStateOf` optimization
- ✅ Explicit navigation structure
- ✅ Shared code between iOS and Android

## Getting Started

### Requirements
- IntelliJ IDEA or Android Studio
- JDK 17+
- Xcode 15+ (for iOS development)
- CocoaPods (for iOS dependencies)

### Installation

```bash
git clone https://github.com/[your-username]/wesplit-kmp.git
cd wesplit-kmp
```

**For Android:**
```bash
./gradlew :composeApp:installDebug
```

**For iOS:**
```bash
cd iosApp
pod install
open iosApp.xcworkspace
```

## Project Structure

```
WeSplit/
├── composeApp/
│   └── src/
│       ├── commonMain/        # Shared code (281 lines)
│       │   └── kotlin/
│       │       ├── App.kt              # Main app & navigation
│       │       ├── HomeScreen.kt       # Main calculator screen
│       │       ├── SelectPeopleScreen.kt
│       │       └── CurrencyFormatter.kt (expect)
│       ├── androidMain/       # Android-specific
│       │   └── CurrencyFormatter.kt (actual)
│       └── iosMain/           # iOS-specific
│           └── CurrencyFormatter.kt (actual)
└── iosApp/                    # iOS app wrapper
```

## Implementation Highlights

- **Expect/actual pattern** - Platform abstraction for currency formatting
- **Navigation graph** - Explicit, scalable navigation structure
- **Separated concerns** - Clear screen, component, and constant organization
- **Performance optimization** - `derivedStateOf` for efficient recomposition
- **Material 3 design** - Modern, consistent UI components

## Read More

📝 **[Building the Same App in SwiftUI, Kotlin Multiplatform, and Flutter — What 281 vs. 75 Lines of Code Teaches Us](https://medium.com/@simonbogutzky/building-the-same-app-in-swiftui-kotlin-multiplatform-and-flutter-what-281-vs-200238dac555)**

A detailed comparison of implementing WeSplit across three frameworks, examining code metrics, architecture patterns, and developer experience.

## Related Projects

- [WeSplit - SwiftUI](https://github.com/simonbogutzky/wesplit-swiftui) - 75 lines
- [WeSplit - Flutter](https://github.com/simonbogutzky/wesplit-flutter) - 238 lines

## Screenshots

![WeSplit KMP Implementation](screenshot.png)

## License

MIT License - see [LICENSE](LICENSE) file for details

## Author

**Simon Bogutzky**  
- Medium: [@simonbogutzky](https://medium.com/@simonbogutzky)
- Senior iOS Engineer @ Open Reply Germany
