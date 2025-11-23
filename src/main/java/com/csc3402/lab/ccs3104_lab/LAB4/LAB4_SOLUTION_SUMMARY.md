# Eight Queens Problem - Lab 4 Solution

## ✅ Implementation Complete!

### 📋 What Was Implemented

1. **EightQueens.java** - Complete implementation of the Eight Queens problem solver
   - Based on Listing 22.11 from Liang textbook (10th Edition)
   - Backtracking algorithm for finding solutions
   - Modern, sleek light blue themed UI
   - Interactive buttons for solving, resetting, and finding new solutions

2. **Features Implemented:**
   - ✨ Modern gradient background (light blue theme)
   - 🎨 Beautiful 8x8 chess board with alternating white and light blue squares
   - ♛ Visual representation using chess queen images/symbols
   - 🔄 Interactive solution finding
   - 📊 Real-time status updates
   - 🎯 Multiple solution variations

3. **UI Components:**
   - Title and subtitle with modern styling
   - Status label showing solution progress
   - Chess board with 8x8 grid
   - Three interactive buttons:
     - **Find New Solution**: Solves from scratch
     - **Reset Board**: Clears the board
     - **Next Solution**: Finds another variation
   - Info section explaining the problem

4. **Algorithm Details:**
   - **Backtracking approach** as described in the textbook
   - Places queens row by row
   - Validates no conflicts (same column, diagonals)
   - Backtracks when no valid position found
   - Time Complexity: O(N!)
   - Space Complexity: O(N)

### 🎨 Visual Design

The UI features a **modern, sleek light blue theme**:
- Gradient background from `#e3f2fd` to `#bbdefb`
- Chess board with white (`#ffffff`) and light blue (`#90caf9`) squares
- Modern rounded buttons with hover effects
- Drop shadows for depth
- Clean, professional typography

### 📁 Files Created

```
LAB4/
├── EightQueens.java                    # Main implementation
└── resources/
    ├── queen.png                        # Queen image (downloaded)
    ├── eight-queens-theme.css          # Optional CSS styling
    ├── README.md                        # Documentation
    ├── QUEEN_IMAGE_INSTRUCTIONS.md     # Image setup guide
    └── LAB4_SOLUTION_SUMMARY.md        # This file
```

### 🚀 How to Run

#### Method 1: Using Maven (Recommended)
```powershell
# Set JAVA_HOME first
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"

# Run the application
.\mvnw.cmd clean javafx:run -Pmain=com.csc3402.lab.ccs3104_lab.LAB4.EightQueens
```

#### Method 2: From IDE
1. Open `EightQueens.java` in IntelliJ IDEA
2. Right-click on the file
3. Select "Run 'EightQueens.main()'"

#### Method 3: Direct Execution
```powershell
# Compile first
.\mvnw.cmd clean compile

# Then run directly
java -cp "target/classes;lib/*" com.csc3402.lab.ccs3104_lab.LAB4.EightQueens
```

### 📊 How the Algorithm Works

1. **Initialization**: Start with an empty board (all positions = -1)

2. **Search Process**:
   - Begin at row 0
   - Try to place a queen in each column (0-7)
   - Check if position is valid using `isValid(row, column)`
   - If valid, place queen and move to next row
   - If no valid position, backtrack to previous row

3. **Validation** (`isValid` method):
   - ✓ Check if another queen is in the same column
   - ✓ Check upper-left diagonal
   - ✓ Check upper-right diagonal

4. **Solution Found**: When all 8 queens are placed (k = 8)

5. **No Solution**: When backtracked past first row (k = -1)

### 🎯 Key Methods

```java
// Main backtracking search algorithm
private boolean search()

// Find valid position in row k
public int findPosition(int k)

// Validate queen placement
public boolean isValid(int row, int column)

// UI display methods
private void displayQueens()
private void displayQueensWithSymbol()
private void clearBoard()
```

### 📸 Expected Output

When you run the application, you will see:
1. A window titled "Eight Queens Problem - Backtracking Solution"
2. A gradient light blue background
3. An 8x8 chess board with alternating colors
4. 8 queens placed on the board (one per row, no conflicts)
5. Three buttons at the bottom for interaction
6. Status message showing solution found

### ✅ Submission Requirements

For PutraBlast submission, include:
1. **Source Code**: `EightQueens.java`
2. **Screenshots**: 
   - Application window showing solved board
   - Different solutions (click "Find New Solution" multiple times)
3. **Documentation**: This README and summary
4. **Demonstration**: Be ready to explain:
   - How the backtracking algorithm works
   - The `search()`, `findPosition()`, and `isValid()` methods
   - Time and space complexity

### 🎓 Learning Outcomes

This lab demonstrates:
- ✅ Backtracking algorithm implementation
- ✅ Recursive problem-solving approach
- ✅ JavaFX UI development
- ✅ Algorithm optimization
- ✅ Data structure usage (1D array for 2D problem)
- ✅ Modern UI/UX design principles

### 📚 References

- Textbook: Introduction to Java Programming (10th Edition) by Y. Daniel Liang
- Section: 22.9 Solving the Eight Queens Problem Using Backtracking
- Listing: 22.11 EightQueens.java
- Animation: https://liveexample.pearsoncmg.com/dsanimation/EightQueens.html
- Code: https://liveexample.pearsoncmg.com/html/EightQueens.html

### 🐛 Troubleshooting

**Issue**: Image not displaying
- **Solution**: The program uses Unicode queen symbol (♛) as fallback

**Issue**: Compilation error
- **Solution**: Ensure JAVA_HOME is set to JDK 21

**Issue**: JavaFX not found
- **Solution**: Dependencies are in pom.xml, run `.\mvnw.cmd clean install`

### 💡 Advanced Features

The implementation includes extras beyond the basic requirement:
- Multiple solution finding
- Interactive reset functionality
- Modern, themed UI (not just basic layout)
- Status feedback
- Hover effects on buttons
- Professional styling with shadows and gradients

---

**Lab Completed**: November 23, 2025
**Course**: CCS3104
**Lab**: Lab 4 - Eight Queens Problem
**Algorithm**: Backtracking
**Status**: ✅ READY FOR DEMONSTRATION
