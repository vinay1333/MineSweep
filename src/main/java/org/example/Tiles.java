package org.example;

public class Tiles {

    private boolean isFlipped;
    private boolean isFlagged;
    private boolean hasMine;
    private int adjacentMines;

    public Tiles(){
        isFlipped = false;
        isFlagged = false;
        hasMine = false;
        adjacentMines = 0;
    }

    public boolean hasMine(){
        return hasMine;
    }

    public void setMine(){
        hasMine = true;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean toggleFlag(){
        if(isFlipped == false){
            return isFlagged = !isFlagged;
        }else{
            System.out.printf("You can only set flags on unflipped tiles!");
            return isFlagged;
        }
    }
    public boolean isFlipped() {
        return isFlipped;
    }

    public int getAdjacentMines(){
        return adjacentMines;
    }

    public void setAdjacentMines(int num){
        adjacentMines = num;
    }
}
