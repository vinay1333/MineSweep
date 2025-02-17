package org.example;


public class GameRunner { // Separate class acting as endpoint for users to interact with game.

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
