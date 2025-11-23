# Eight Queens Problem - LAB 4

## Overview
This implementation solves the classic Eight Queens problem using a backtracking algorithm, based on Listing 22.11 from the Liang textbook (10th Edition).

## Problem Description
The Eight Queens problem is to find a solution to place a queen in each row on a chessboard such that no two queens can attack each other.

## Algorithm: Backtracking
The backtracking approach searches for a candidate solution incrementally, abandoning that option as soon as it determines that the candidate cannot possibly be a valid solution, and then looks for a new candidate.

### How It Works:
1. Start from the first row (k=0)
2. Try to place a queen in each column (j=0 to 7)
3. Check if the position is valid (no conflicts with previously placed queens)
4. If valid, move to the next row
5. If not valid or no valid position found, backtrack to the previous row
6. Continue until all 8 queens are placed successfully

## Features
- ✨ Modern, sleek light blue themed UI
- 🎨 Beautiful gradient background
- ♛ Visual representation of the chess board
- 🔄 Multiple solution finding
- 📊 Real-time status updates
- 🎯 Interactive buttons for solving, resetting, and finding next solutions

## UI Elements
- **Title**: Eight Queens Problem Solver
- **Chess Board**: 8x8 grid with alternating white and light blue squares
- **Queens**: Displayed using chess queen symbol (♛) or image
- **Buttons**:
  - Find New Solution: Solves the puzzle from scratch
  - Reset Board: Clears the board
  - Next Solution: Finds another solution variation

## Queen Image
To use the queen image instead of the symbol:
1. Download the queen image from: https://liveexample.pearsoncmg.com/common/image/queen.jpg
2. Save it as `queen.png` in this directory
3. The program will automatically use the image if available

Alternatively, the program uses the Unicode chess queen symbol (♛) as a fallback.

## Running the Application
```bash
# From the project root directory
mvn clean javafx:run
```

Or run the main method in `EightQueens.java` directly from your IDE.

## Algorithm Complexity
- **Time Complexity**: O(N!) where N is the size of the board
- **Space Complexity**: O(N) for the queens array and recursion stack

## Key Methods
1. **search()**: Main backtracking algorithm that searches for a solution
2. **findPosition(k)**: Finds a valid column position for a queen in row k
3. **isValid(row, column)**: Validates if a queen can be placed at the given position
   - Checks column conflicts
   - Checks upper-left diagonal conflicts
   - Checks upper-right diagonal conflicts

## References
- Textbook: Introduction to Java Programming (10th Edition) by Y. Daniel Liang
- Section: 22.9 Solving the Eight Queens Problem Using Backtracking
- Animation: https://liveexample.pearsoncmg.com/dsanimation/EightQueens.html

## Author
Created for CCS3104 Lab 4
