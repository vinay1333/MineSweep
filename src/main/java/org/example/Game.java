package org.example;

import java.util.Scanner;

public class Game {
    private Board board;
    private Scanner scanner;

    public Game(){
        board = new Board();
        scanner = new Scanner(System.in);
    }

    public void play() { // Method to play game
        boolean playAgain = true; // Give player restart game option

        while (playAgain) { // While player wants to restart
            startNewGame();
            playAgain = askPlayAgain();
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private void startNewGame() { // Method to start new game
        board = new Board();  // Reset board for a new game
        boolean gameOver = false;

        while (!gameOver) { // Runs as long as game over is false
            board.displayBoard(false);
            System.out.print("Enter action (R for reveal, F for flag) followed by row and column: ");

            String input = scanner.next();

            int x, y;

            if (!scanner.hasNextInt()) { // Validate input for x, must be an integer
                System.out.println("Invalid input. Please enter a valid row");
                scanner.nextLine();
                continue;
            }
            x = scanner.nextInt();

            if (!scanner.hasNextInt()) { // Validate input for x, must be an integer
                System.out.println("Invalid input. Please enter a valid column.");
                scanner.nextLine();
                continue;
            }
            y = scanner.nextInt();

            if (!board.isValid(x, y)) { // Validate input positions, ensure they are within grid range.
                System.out.println("Invalid coordinates. Try again.");
                continue;
            }

            if (input.equalsIgnoreCase("R")) { // Accept input of upper and lowercase R
                if (board.flipTile(x, y)) { // If flip tile returns false, tile is flipped.
                    System.out.println("Game Over! You hit a mine.");// If flip tile returns true, a mine is hit and the game is over.
                    board.displayBoard(true);
                    gameOver = true;
                }
            } else if (input.equalsIgnoreCase("F")) { // Accept input of upper and lowercase F
                board.toggleFlag(x, y);
            } else {
                System.out.println("Invalid action. Use 'R' to reveal or 'F' to flag.");
                continue;
            }

            if (board.checkWin()) { // Checks if user has won.
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
}





