MiniBoard is a simple Android application for creating, viewing, and managing threads and comments. It uses modern Android development practices, including MVVM architecture, Retrofit for API communication, and RecyclerView for displaying threaded content.

Features:

Create Threads: Start new discussions by creating threads.
View Comments: Browse through comments in a threaded structure.
Nested Comments: Support for displaying nested comments with visual indentation.
Reply to Comments: Add replies to specific comments using long-press functionality.
Seamless Navigation: Navigate between threads and comments with smooth transitions.
Modern UI: Clean and user-friendly interface designed with Material Design principles.

Architecture:

This app follows the MVVM (Model-View-ViewModel) architectural pattern to ensure a clean separation of concerns and scalability.

Key Technologies and Libraries:

Kotlin: Entire app is written in Kotlin.
Retrofit: For network communication with the backend API.
RecyclerView: For efficiently displaying threads and comments.
Navigation Component: For handling fragment navigation.
ViewModel and LiveData: For managing and observing UI-related data in a lifecycle-conscious way.
