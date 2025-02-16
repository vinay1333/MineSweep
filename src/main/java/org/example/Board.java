package org.example;

import java.util.Random;

public class Board {

    private static final int boardSize = 8;
    private static final int noMines = 10;
    private Tile[][] grid;
    private boolean isFirstMove;

    public Board(){

        isFirstMove = true;
        grid = new Tile[boardSize][boardSize];

        setBoard();
        placeMines();
        calculateNumbers();
    }


    public boolean getFirst(){
        return isFirstMove;
    }
    public void setFirst() {
        isFirstMove = false;
    }

    private void setBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                grid[i][j] = new Tile();
            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int placedMines = 0;
        while (placedMines < noMines) {
            int x = random.nextInt(boardSize);
            int y = random.nextInt(boardSize);
            if (!grid[x][y].isMine()) {
                grid[x][y].setMine(true);
                placedMines++;
            }
        }
    }

    public boolean isValid(int x, int y) {
        return (x >= 0 && x < boardSize) && (y >= 0 && y < boardSize);

    }

    private void calculateNumbers() { // Calculates the number of adjacent mines to a tile
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (grid[i][j].isMine()){ // Skip if tile is a mine (shouldn't display count)
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

    private void relocateMine(int x, int y) {
        grid[x][y].setMine(false); // Remove mine from current position.
        Random random = new Random(); // Generate new random position for mine.
        while (true) {
            int newX = random.nextInt(boardSize);
            int newY = random.nextInt(boardSize);
            if (!grid[newX][newY].isMine()) { // Check if new position is already a mine.
                grid[newX][newY].setMine(true); // If not then place mine in new position, otherwise regenerate new position
                break; // Exit loop when new valid position is found
            }
        }
        calculateNumbers();
    }

    public boolean flipTile(int x, int y) { // Handles logic for flipping tiles and game over. Returns true to end game, false to continue.

        if (!isValid(x, y) || grid[x][y].isFlipped() || grid[x][y].isFlagged()){ // Doesn't respond to invalid, flipped or flagged tiles.
            return false;
        }

        if (isFirstMove && grid[x][y].isMine()) { // If first move and player has landed on a mine, relocate the mine.
            relocateMine(x, y);
            isFirstMove = false; // Change value for isFirstMove to false.
        }

        grid[x][y].setFlipped(true); // Change value to indicate tile has now been flipped

        if (grid[x][y].isMine()) {
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
        return false;
    }

    public void toggleFlag(int x, int y) {
        if (!isValid(x, y) || grid[x][y].isFlipped()){ // Doesn't respond to invalid co-ordinates or if a selected tile is already flipped.
            return;
        }
        grid[x][y].toggleFlag();
    }

    public void displayBoard(boolean revealAll) {
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

    public boolean checkWin() { // Check if all non mines have been revealed
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
