package org.example;

import java.util.Scanner;

public class Game {
    private Board board;
    private Scanner scanner;
    private Difficulty difficulty = null;

    public Game() {
        scanner = new Scanner(System.in);
    }

    public void play() { // Method to play game
        boolean playAgain = true; // Give player restart game option

        while (playAgain) { // While player wants to restart
            selectDifficulty();
            board = new Board(difficulty);
            startNewGame();
            playAgain = askPlayAgain();
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private void startNewGame() { // Method to start new game
        board = new Board(difficulty);  // Reset board for a new game
        boolean gameOver = false;

        while (!gameOver) { // Runs as long as game over is false
            board.displayBoard(false);
            System.out.print("Enter action (R for reveal, F for flag) followed by row and column: ");

            String input = scanner.next();

            int x, y;

            if (!scanner.hasNextInt()) { // Validate input for x, must be an integer
                System.out.println("Invalid input. Please enter a valid row.");
                scanner.nextLine();
                continue;
            }
            x = scanner.nextInt();

            if (!scanner.hasNextInt()) { // Validate input for y, must be an integer
                System.out.println("Invalid input. Please enter a valid column.");
                scanner.nextLine();
                continue;
            }
            y = scanner.nextInt();

            if (!board.isValid(x, y)) { // Validate input positions, ensure they are within grid range
                System.out.println("Invalid coordinates. Try again.");
                continue;
            }

            if (input.equalsIgnoreCase("R")) { // Accept input of upper and lowercase R
                if (board.flipTile(x, y)) { // If flip tile returns false, tile is flipped
                    System.out.println("Game Over! You hit a mine.");
                    board.displayBoard(true);
                    gameOver = true;
                }
            } else if (input.equalsIgnoreCase("F")) { // Accept input of upper and lowercase F
                board.toggleFlag(x, y);
            } else {
                System.out.println("Invalid action. Use 'R' to reveal or 'F' to flag.");
                continue;
            }

            if (board.checkWin()) { // Checks if user has won
                System.out.println("Congratulations! You cleared the board.");
                board.displayBoard(true);
                gameOver = true;
            }
        }
    }

    // Method to ask if the player wants to play again
    private boolean askPlayAgain() {
        System.out.print("Play again? (Y/N): ");
        String playAgainInput = scanner.next();
        return playAgainInput.equalsIgnoreCase("Y"); // If "Y" is entered value returns true and game will ask to play again, else false and game stops.
    }

    private void selectDifficulty() {
        while (difficulty == null) {
            System.out.print("Enter game difficulty (EASY/MED/HARD): ");
            String input1 = scanner.next();

            for (Difficulty d : Difficulty.values()) {
                if (d.name().equalsIgnoreCase(input1)) {
                    difficulty = d;
                    return; // Exit the loop once a valid difficulty is selected
                }
            }

            // If the input is invalid, inform the user and prompt again
            System.out.println("Invalid difficulty. Please enter one of the following: EASY, MED, or HARD.");
        }
    }
}






