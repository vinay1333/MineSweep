package org.example;

public enum Difficulty {

    EASY(8,5),
    MED(10,15),
    HARD(12,25);

    private final int boardSize;
    private final int noMines;

    Difficulty(int boardSize, int noMines){
        this.boardSize = boardSize;
        this.noMines = noMines;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getNoMines() {
        return noMines;
    }
}
