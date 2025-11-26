# Eight Queens Simple - Multithreading Explanation

## Overview
This is a simplified version of the Eight Queens solver that demonstrates **multithreading** in JavaFX.

## Multithreading Concepts Demonstrated

### 1. **Background Thread for Computation**
```java
Thread searchThread = new Thread(() -> {
    // This code runs in a separate thread
    boolean found = findNextSolution();
});
searchThread.start();
```

**Why?** 
- The search algorithm can take time
- Without threading, the UI would freeze during the search
- User couldn't interact with buttons while searching

### 2. **Platform.runLater() for UI Updates**
```java
Platform.runLater(() -> {
    // This code runs on the JavaFX Application Thread
    counterLabel.setText("Solutions found: " + solutionCounter);
    updateBoard();
});
```

**Why?**
- JavaFX has a rule: **Only the JavaFX Application Thread can update the UI**
- Background threads cannot directly modify UI elements
- `Platform.runLater()` schedules UI updates on the correct thread

### 3. **Thread Safety with isSearching Flag**
```java
private boolean isSearching = false;

if (isSearching) {
    return; // Prevent multiple simultaneous searches
}
isSearching = true;
```

**Why?**
- Prevents user from clicking "Next Solution" multiple times rapidly
- Avoids race conditions where two threads modify `queens[]` array simultaneously
- Ensures only one search happens at a time

### 4. **Daemon Thread**
```java
searchThread.setDaemon(true);
```

**Why?**
- Daemon threads automatically stop when the application closes
- Non-daemon threads would keep the application running even after closing the window
- Good practice for background worker threads

## Thread Flow Diagram

```
User clicks "Next Solution" button
        ↓
findNextSolutionThreaded() called (on JavaFX Thread)
        ↓
Create new Thread for search
        ↓
        ├─────────────────────────┐
        ↓                         ↓
JavaFX Thread              Background Thread
(UI remains                (runs findNextSolution)
 responsive)                      ↓
        ↓                  Performs backtracking
        ↓                  algorithm
        ↓                         ↓
        ↓                  Search completes
        ↓                         ↓
        ↓                  Platform.runLater()
        ↓                         ↓
        └─────────────────────────┘
                  ↓
        Update UI (counterLabel, board)
                  ↓
        Set isSearching = false
```

## Key Differences from Advanced Version

1. **Simple UI**: No gradients, shadows, or fancy styling
2. **Basic Layout**: Simple BorderPane with GridPane
3. **Simple Functions**: No complex styling methods
4. **Clear Threading**: Explicit thread creation and management
5. **Counter**: Shows how many solutions found
6. **Two Buttons**: Reset and Next Solution only

## How to Run

```java
// From your IDE
Right-click EightQueensSimple.java → Run

// Or from command line
javac EightQueensSimple.java
java EightQueensSimple
```

## Features

- ✅ Simple black and white checkerboard
- ✅ Red queen symbols (♛)
- ✅ Solution counter
- ✅ Reset button
- ✅ Next Solution button
- ✅ Multithreaded search (non-blocking UI)
- ✅ Easy to understand code

## Multithreading Benefits

1. **Responsive UI**: Window doesn't freeze during search
2. **Better User Experience**: Buttons remain clickable
3. **Proper Resource Management**: Threads clean up automatically
4. **Thread Safety**: Prevents concurrent modification issues

## Common Threading Mistakes (Avoided Here)

❌ **Updating UI from background thread** (would crash)
```java
// WRONG - Don't do this!
Thread t = new Thread(() -> {
    counterLabel.setText("Wrong!"); // IllegalStateException!
});
```

✅ **Correct way**
```java
Thread t = new Thread(() -> {
    Platform.runLater(() -> {
        counterLabel.setText("Correct!");
    });
});
```

❌ **Not using thread safety** (race conditions)
```java
// WRONG - Multiple threads can modify queens[] simultaneously
```

✅ **Correct way**
```java
if (isSearching) return; // Only one search at a time
isSearching = true;
```

## Learning Points

1. **Thread Creation**: `new Thread(() -> { ... })`
2. **UI Thread Safety**: `Platform.runLater(() -> { ... })`
3. **Daemon Threads**: `thread.setDaemon(true)`
4. **Synchronization**: Using boolean flags
5. **Background Processing**: Separating computation from UI

## Comparison: With vs Without Threading

### Without Threading (UI Freezes):
```java
findNextSolution(); // UI frozen here
updateBoard();      // UI updates after search
```

### With Threading (UI Responsive):
```java
Thread t = new Thread(() -> {
    findNextSolution();           // Runs in background
    Platform.runLater(() -> {
        updateBoard();            // UI updates when ready
    });
});
t.start(); // UI remains responsive immediately
```

---

**Simple, clean, and demonstrates multithreading!**
