# BookNest 

BookNest is a modern, cross-platform book discovery and management application built with Kotlin Multiplatform and Jetpack Compose. It allows users to search for books, view details, and manage their favorite books across Android, iOS, and Desktop platforms.

## Demo 🎥

### Screenshots

#### Android
<img src="demo/android_home.png" width="300" alt="Android Home Screen">
<img src="demo/android_details.png" width="300" alt="Android Book Details">
<img src="demo/android_favorites.png" width="300" alt="Android Favorites">

#### Desktop
<img src="demo/desktop_home.png" width="400" alt="Desktop Home Screen">
<img src="demo/desktop_details.png" width="400" alt="Desktop Book Details">
<img src="demo/desktop_favorites.png" width="400" alt="Desktop Favorites">


### Key Features Demo

1. **Book Search**
   - Real-time search with Open Library API
   - Instant results with book covers
   - Smooth animations and transitions

2. **Book Details**
   - Comprehensive book information
   - Beautiful cover image display
   - Author information and publication details
   - Ratings and reviews
   - Add to favorites functionality

3. **Favorites Management**
   - Save books for offline access
   - Easy-to-use favorites interface
   - Quick access to saved books
   - Smooth animations when adding/removing favorites

## Features 

- **Cross-Platform**: Works on Android, iOS, and Desktop
- **Book Search**: Search for books using the Open Library API
- **Book Details**: View comprehensive book information including:
  - Cover images
  - Author information
  - Publication details
  - Ratings
  - Page count
  - Language
  - Synopsis
- **Favorites**: Save and manage your favorite books
- **Modern UI**: Beautiful Material 3 design with smooth animations
- **Offline Support**: Access your favorite books even without internet

## Tech Stack 

- **Kotlin Multiplatform**: For cross-platform development
- **Jetpack Compose**: For modern UI development
- **Ktor**: For network requests
- **Room**: For local database
- **Koin**: For dependency injection
- **Coil**: For image loading
- **Open Library API**: For book data

## Project Structure 📁

```
composeApp/
├── src/
│   ├── commonMain/          # Shared code
│   ├── androidMain/         # Android-specific code
│   ├── iosMain/            # iOS-specific code
│   └── desktopMain/        # Desktop-specific code
```

## Getting Started 🚀

### Prerequisites

- Android Studio Hedgehog or newer
- JDK 17 or newer
- For iOS development: macOS and Xcode
- For Desktop development: JDK 17

### Building the Project

1. Clone the repository:
```bash
git clone https://github.com/yourusername/BookNest.git
cd BookNest
```

2. Open the project in Android Studio

3. Build and run:

For Android:
```bash
./gradlew :composeApp:installDebug
```

For Desktop:
```bash
./gradlew :composeApp:run
```

For iOS (requires macOS):
```bash
./gradlew :composeApp:embedAndSignAppleFrameworkForXcode
```

## Architecture 🏗

The project follows Clean Architecture principles with the following layers:

- **Presentation**: UI components and ViewModels
- **Domain**: Business logic and use cases
- **Data**: Repository implementations and data sources
- **Core**: Common utilities and extensions

## Sequence Diagram

```mermaid
%%{init: {'theme': 'base', 'themeVariables': { 'background': '#ffffff'}}}%%
sequenceDiagram
    actor User
    participant Screen as BookListScreen
    participant VM as BookListViewModel
    participant Repo as BookRepository
    participant Remote as RemoteBookDataSource
    participant Local as FavoriteBookDao
    participant API as Open Library API
    participant DB as Local Database

    %% Book Search Flow
    User->>Screen: Enter search query
    activate Screen
    Screen->>VM: searchQueryChange(query)
    activate VM
    VM->>Repo: searchBooks(query)
    activate Repo
    Repo->>Remote: searchBooks(query)
    activate Remote
    Remote->>API: GET /search.json
    activate API
    API-->>Remote: Search results
    deactivate API
    Remote-->>Repo: Result<List<Book>>
    deactivate Remote
    Repo-->>VM: Result<List<Book>>
    deactivate Repo
    VM-->>Screen: Update UI with results
    deactivate VM
    Screen-->>User: Display search results
    deactivate Screen

    %% Book Details Flow
    User->>Screen: Click on book
    activate Screen
    Screen->>VM: onBookClick(book)
    activate VM
    VM->>Repo: getBookDescription(bookId)
    activate Repo
    alt Book in favorites
        Repo->>Local: getFavoriteBook(bookId)
        activate Local
        Local->>DB: Query book
        activate DB
        DB-->>Local: Book data
        deactivate DB
        Local-->>Repo: Book data
        deactivate Local
    else Book not in favorites
        Repo->>Remote: getBookDetails(bookId)
        activate Remote
        Remote->>API: GET /works/{id}
        activate API
        API-->>Remote: Book details
        deactivate API
        Remote-->>Repo: Book details
        deactivate Remote
    end
    Repo-->>VM: Book details
    deactivate Repo
    VM-->>Screen: Update UI with details
    deactivate VM
    Screen-->>User: Display book details
    deactivate Screen

    %% Add to Favorites Flow
    User->>Screen: Click favorite button
    activate Screen
    Screen->>VM: markAsFavorite(book)
    activate VM
    VM->>Repo: markAsFavorite(book)
    activate Repo
    Repo->>Local: upsert(book)
    activate Local
    Local->>DB: Save book
    activate DB
    DB-->>Local: Success
    deactivate DB
    Local-->>Repo: Success
    deactivate Local
    Repo-->>VM: Success
    deactivate Repo
    VM-->>Screen: Update UI
    deactivate VM
    Screen-->>User: Show confirmation
    deactivate Screen
```