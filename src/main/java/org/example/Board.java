package org.example;

import java.util.Random;

public class Board {

    private static final int boardSize = 8;
    private static final int noMines = 10;
    private Tile[][] grid; // Array of tiles represent board grid
    private boolean isFirstMove;

    public Board(){ // Board constructor

        isFirstMove = true;
        grid = new Tile[boardSize][boardSize];

        setBoard();
        placeMines();
        calculateAdjMines();
    }


    public boolean getFirst(){
        return isFirstMove;
    }
    public void setFirst() {
        isFirstMove = false;
    }

    private void setBoard() { // Initialise the grid with tile objects, setting their positions.
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                grid[i][j] = new Tile();
            }
        }
    }

    private void placeMines() { // Randomly generates positions to place mines on grid
        Random random = new Random(); // Random Object to generate random values
        int placedMines = 0;
        while (placedMines < noMines) { // Run until the number of placed mines is equal to number of mines.
            int x = random.nextInt(boardSize);
            int y = random.nextInt(boardSize);
            if (!grid[x][y].isMine()) { // If new position is not already a mine, set mine position.
                grid[x][y].setMine(true);
                placedMines++; // Increment number of placed mines
            }
        }
    }

    public boolean isValid(int x, int y) { // Checks if input values fall within the range of boardSize
        return (x >= 0 && x < boardSize) && (y >= 0 && y < boardSize);

    }

    private void calculateAdjMines() { // Calculates the number of adjacent mines to a tile
        for (int i = 0; i < boardSize; i++) { // Loops through rows and columns of grid
            for (int j = 0; j < boardSize; j++) {
                if (grid[i][j].isMine()){ // If current tile is a mine, then move the next
                    continue;
                }
                int count = 0; // variable to hold mine count.
                for (int dx = -1; dx <= 1; dx++) { // Loop through rows to above, in-line and below current tile.
                    for (int dy = -1; dy <= 1; dy++) { //Loop through columns to the left, in-line and to the right of current tile.
                        int nx = i + dx, ny = j + dy; //Calculate co-ordinates of neighbouring tile.
                        if (isValid(nx, ny) && grid[nx][ny].isMine()) { // Check if neighbouring tile is valid and is a mine
                            count++; //Increment mine count
                        }
                    }
                }
                grid[i][j].setAdjacentMines(count); // Set tile's adjacent mine count
            }
        }
    }

    private void relocateMine(int x, int y) { // Safety method to ensure first move is not a mine.
        grid[x][y].setMine(false); // Remove mine from current position.
        Random random = new Random(); // Generate new random position for mine.
        while (true) { // Runs until break key word
            int newX = random.nextInt(boardSize);
            int newY = random.nextInt(boardSize);
            if (!grid[newX][newY].isMine()) { // Check if new position is already a mine.
                grid[newX][newY].setMine(true); // If not then place mine in new position, otherwise regenerate new position
                break; // Exit loop when new valid position is found
            }
        }
        calculateAdjMines(); // Re-calcualte adjacent mines count on board.
    }

    public boolean flipTile(int x, int y) { // Handles logic for flipping tiles and game over. Returns true to end game, false to continue.

        if (!isValid(x, y) || grid[x][y].isFlipped() || grid[x][y].isFlagged()){ // Checks if tile position is invalid, flipped or flagged.
            return false;
        }

        if (isFirstMove && grid[x][y].isMine()) { // If player's first move and has landed on a mine, relocate the mine.
            relocateMine(x, y);
            isFirstMove = false; // Change value for isFirstMove to false.
        }

        grid[x][y].setFlipped(true); // Indicate tile has now been flipped.

        if (grid[x][y].isMine()) { // If flipped tile is a mine, return true and end game.
            return true;
        }

        if (grid[x][y].getAdjacentMines() == 0) { // Check if current tile has no adjacent mines.
            for (int dx = -1; dx <= 1; dx++) { // Loop through rows above, in-line and below current tile.
                for (int dy = -1; dy <= 1; dy++) { // Loop through columns to left, in-line and to the right of current tile.
                    if (dx != 0 || dy != 0){ //skip looping through current tile
                        flipTile(x + dx, y + dy); // Flip the neighbouring tile if not already flipped.
                    }
                }
            }
        }
        return false; // Tile has been flipped and game continues.
    }

    public void toggleFlag(int x, int y) {
        if (!isValid(x, y) || grid[x][y].isFlipped()){ // Prevent players from flagging invalid or flipped tiles.
            return;
        }
        grid[x][y].toggleFlag();
    }

    public void displayBoard(boolean revealAll) { // Parameter set to F while game continues, changed to T if win/lose.
        System.out.print("  ");
        for (int i = 0; i < boardSize; i++){ //Prints the Column Numbers.
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < boardSize; i++) {
            System.out.print(i + " "); // Print Row number followed by space.
            for (int j = 0; j < boardSize; j++) {
                if (revealAll || grid[i][j].isFlipped()) { // If revealAll is true or a tile is flipped.
                    if (grid[i][j].isMine()) System.out.print("* "); // Prints mine as star.
                    else if (grid[i][j].getAdjacentMines() == 0){
                        System.out.print("  "); // Prints empty space for Tiles that do not have adjacent mines.
                    }
                    else{
                        System.out.print(grid[i][j].getAdjacentMines() + " "); //Prints number of adjacent mines for tile.
                    }
                } else if (grid[i][j].isFlagged()) {
                    System.out.print("F "); // Prints Flag as "F"
                } else {
                    System.out.print("- "); // Prints "-" for unflipped tiles
                }
            }
            System.out.println();
        }
    }

    public boolean checkWin() { // Check if all non mines have been revealed on grid.
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (!grid[i][j].isFlipped() && !grid[i][j].isMine()){ // Checks if a tile is not revealed yet and is not a mine.
                    return false;
                }
            }
        }
        return true;
    }


}
