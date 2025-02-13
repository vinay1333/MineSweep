package org.example;

import java.io.InputStream;
import java.util.Scanner;

public class Game {
    private Board board;
    private boolean gameOver;
    private Scanner scanner;

    public Game(int rows, int col, int mines){
        Board board = new Board(rows,col,mines);
        boolean gameOver = false;
        Scanner scanner = new Scanner(System.in);
    }


}
