package org.example;

public class Board {

    private int rows;
    private int col;
    private int mines;
    private Tiles[][] tiles;
    private boolean first;

    public Board(int inRows, int inCol, int inMines){

        first = true;
        rows = inRows;
        col = inCol;
        mines = inMines;
        Tiles [][] tiles = new Tiles[inRows][inCol];

        setBoard();
    }

    public int getRows(){
        return this.rows;
    }

    public int getCol(){
        return this.col;
    }

    public boolean getFirst(){
        return first;
    }
    public void setFirst(){
        first = false;
    }

    public static Tiles[][] setBoard(){
    }

}
