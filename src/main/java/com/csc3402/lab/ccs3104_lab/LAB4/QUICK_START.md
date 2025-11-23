# 🚀 Quick Start Guide - Lab 4: Eight Queens Problem

## Fastest Way to Run

### Option 1: Double-Click Run (Easiest)
1. Double-click `run_eight_queens.bat` in the project root
2. Wait for compilation (first time only)
3. Application window will open automatically

### Option 2: PowerShell
```powershell
.\run_eight_queens.ps1
```

### Option 3: From Your IDE (IntelliJ IDEA)
1. Navigate to: `src/main/java/com/csc3402/lab/ccs3104_lab/LAB4/EightQueens.java`
2. Right-click on the file
3. Select "Run 'EightQueens.main()'"

### Option 4: Maven Command
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
.\mvnw.cmd clean javafx:run -Djavafx.mainClass=com.csc3402.lab.ccs3104_lab.LAB4.EightQueens
```

---

## 🎮 How to Use the Application

### When the Window Opens:
1. You'll see a beautiful **light blue themed** chess board
2. 8 queens are already placed (initial solution)
3. The status bar shows "Solution found!"

### Buttons:
- **Find New Solution** 🔄: Generates a fresh solution
- **Reset Board** 🔲: Clears the board completely
- **Next Solution** ➡️: Finds another valid solution

### Try This:
1. Click "Find New Solution" several times
2. Notice how queens are placed differently each time
3. All solutions are valid (no queens attack each other)

---

## 📝 For Your Lab Demonstration

### Be Ready to Explain:

1. **The Backtracking Algorithm**:
   - Starts at row 0
   - Tries to place queen in each column
   - Checks if placement is valid
   - Moves to next row if valid, backtracks if not

2. **The `isValid()` Method**:
   - Checks same column: `queens[i] == column`
   - Checks upper-left diagonal: `queens[i] - column == i - row`
   - Checks upper-right diagonal: `queens[i] - column == row - i`

3. **Time Complexity**: O(N!) - factorial time
4. **Space Complexity**: O(N) - array and recursion stack

### Key Code to Show Instructor:
```java
// Main search method (lines 205-226)
private boolean search()

// Position finder (lines 233-243)
public int findPosition(int k)

// Validator (lines 254-267)
public boolean isValid(int row, int column)
```

---

## 📸 Screenshots for PutraBlast

Take screenshots of:
1. ✅ Initial solution (when app first opens)
2. ✅ After clicking "Find New Solution" (different arrangement)
3. ✅ The code (showing the three main methods)
4. ✅ The running application with all UI elements visible

---

## ✅ Checklist Before Submission

- [ ] Code compiles without errors
- [ ] Application runs and displays chess board
- [ ] Queens are placed correctly (one per row)
- [ ] No two queens attack each other
- [ ] UI is modern with light blue theme
- [ ] Buttons work correctly
- [ ] Screenshots taken
- [ ] Can explain the algorithm

---

## 🎨 What Makes This Implementation Special

✨ **Modern UI Design**:
- Gradient light blue background (#e3f2fd to #bbdefb)
- Sleek rounded buttons with shadows
- Professional typography
- Hover effects on buttons

✨ **Beyond Basic Requirements**:
- Interactive solution finding
- Multiple solution variations
- Real-time status updates
- Fallback to Unicode symbol if image missing
- Clean, commented code

✨ **Code Quality**:
- Well-documented with Javadoc comments
- Follows naming conventions
- Proper error handling
- Modular design

---

## 🆘 Troubleshooting

**Problem**: Application doesn't start
- **Fix**: Run `.\mvnw.cmd clean compile` first

**Problem**: "JAVA_HOME not set" error
- **Fix**: Edit the run script to point to your JDK installation

**Problem**: Chess board appears but no queens
- **Fix**: Check that `queen.png` is in resources folder
  (The app will use ♛ symbol as fallback)

**Problem**: Window is too small/large
- **Fix**: Window is fixed at 650x850, perfectly sized for the board

---

## 📚 Files You Should Know

### Java Files:
- `LAB4/EightQueens.java` - Main implementation (270 lines)

### Resource Files:
- `LAB4/queen.png` - Queen image
- `LAB4/eight-queens-theme.css` - Optional styling (not required)

### Documentation:
- `LAB4/README.md` - Detailed explanation
- `LAB4/LAB4_SOLUTION_SUMMARY.md` - Complete summary
- `LAB4/QUEEN_IMAGE_INSTRUCTIONS.md` - Image setup guide
- `LAB4/QUICK_START.md` - This file!

### Run Scripts:
- `run_eight_queens.bat` - Windows batch file
- `run_eight_queens.ps1` - PowerShell script

---

## 💡 Quick Facts

- **Lines of Code**: ~270
- **Algorithm**: Backtracking
- **UI Framework**: JavaFX
- **Java Version**: 21
- **Dependencies**: OpenJFX 21
- **Board Size**: 8×8 (standard chess board)
- **Number of Solutions**: 92 (for 8×8 board)

---

**Ready to demonstrate?** ✅
Run the app, explain the algorithm, show different solutions, and you're good to go!

**Questions?** Check the detailed README.md in the LAB4 folder.

---

*Created for CCS3104 Lab 4 - November 23, 2025*
