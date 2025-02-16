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

    private void calculateNumbers() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (grid[i][j].isMine()) continue;
                int count = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = i + dx, ny = j + dy;
                        if (isValid(nx, ny) && grid[nx][ny].isMine()) {
                            count++;
                        }
                    }
                }
                grid[i][j].setAdjacentMines(count);
            }
        }
    }


}
