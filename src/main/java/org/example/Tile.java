package org.example;

public class Tile {

    private boolean isFlipped;
    private boolean isFlagged;
    private boolean isMine;
    private int adjacentMines;

    public Tile(){
        isFlipped = false;
        isFlagged = false;
        isMine = false;
        adjacentMines = 0;
    }

    public boolean isMine(){
        return isMine;
    }

    public void setMine(boolean mine){
        isMine = mine;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped){
        isFlipped = flipped;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void toggleFlag(){
        isFlagged = !isFlagged;
    }

    public int getAdjacentMines(){
        return adjacentMines;
    }

    public void setAdjacentMines(int num){
        adjacentMines = num;
    }
}
