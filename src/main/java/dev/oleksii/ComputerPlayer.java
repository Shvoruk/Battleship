package dev.oleksii;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * ComputerPlayer is a concrete subclass of Player.
 * It automates ship placement and move selection using random choices.
 */
public class ComputerPlayer extends Player {

    // Random instance used for generating random positions and orientations.
    private final Random random;

    /**
     * Constructs a ComputerPlayer with a given Random object.
     * The player's name is set to "Computer".
     *
     * @param random the Random instance for generating random numbers.
     */
    public ComputerPlayer(Random random) {
        super("Computer");
        this.random = random;
    }

    /**
     * Automatically places ships on the board.
     * For each ship, the method randomly selects a starting coordinate and orientation
     * until the ship is successfully placed or a maximum number of attempts is reached.
     *
     * @param scanner a Scanner object (not used here, but required by the abstract method signature).
     */
    @Override
    public void placeShips(Scanner scanner) {
        int boardSize = getBoard().getSize();  // Retrieve the board size.

        // Loop through each ship that needs to be placed.
        for (Ship ship : getShips()) {
            boolean placed = false;
            int attempts = 0;
            int maxAttempts = 1000;  // Maximum number of attempts before giving up.

            // Continue attempting to place the current ship until successful.
            while (!placed) {
                attempts++;
                if (attempts > maxAttempts) {
                    System.err.println("[WARNING] Computer could not place "
                                       + ship.getName() + " after " + maxAttempts + " attempts.");
                    // Either break or throw an exception based on desired error handling.
                    break;
                }

                // Generate random starting coordinates.
                int row = random.nextInt(boardSize);
                int col = random.nextInt(boardSize);
                // Randomly choose an orientation: true for horizontal, false for vertical.
                boolean horizontal = random.nextBoolean();

                // Attempt to place the ship on the board.
                if (getBoard().placeShip(ship, row, col, horizontal, false)) {
                    placed = true;  // Ship was placed successfully.
                }
            }
        }
    }

    /**
     * Automatically takes a turn by randomly selecting a coordinate on the opponent's board.
     * The method ensures the chosen cell hasn't been targeted before.
     * Depending on whether the shot is a hit or a miss, the board is updated and an appropriate message is displayed.
     *
     * @param scanner  a Scanner object (not used for input here, but required by the method signature).
     * @param opponent the opposing player whose board is targeted.
     * @return a Move object representing the shot taken.
     */
    @Override
    public Move takeTurn(Scanner scanner, Player opponent) {
        int boardSize = opponent.getBoard().getSize();
        int row, col;

        // Loop until a cell that hasn't been hit is found.
        do {
            row = random.nextInt(boardSize);
            col = random.nextInt(boardSize);
        } while (opponent.getBoard().getGameState().get(new Coordinate(row, col)).isHit());

        // Inform the user about the computer's guess.
        System.out.println("Computer guesses: " + (char) ('A' + row) + (col + 1));

        // Pause briefly to allow the user to see the guess.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // If interrupted, ignore or handle as necessary.
        }

        // Retrieve the opponent's board and the corresponding cell.
        var opponentBoard = opponent.getBoard();
        Cell cell = opponentBoard.getGameState().get(new Coordinate(row, col));
        char result;

        // Process the move based on whether a ship occupies the cell.
        if (cell.hasShip()) {
            // Register a hit on the ship at the specified coordinate.
            cell.getShip().checkHit(row, col);

            if (cell.getShip().isSunk()) {
                // If the ship is sunk, mark all of its coordinates with 'X' on the opponent's board.
                for (Coordinate c : cell.getShip().getCoordinates()) {
                    opponentBoard.updateCell(c.row(), c.col(), 'X');
                }
                MessageFormatter.printBoxedMessage(cell.getShip().getName() + " sunk!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // Handle interruption if needed.
                }
                result = 'X';
            } else {
                // If the ship is hit but not sunk, mark the cell with 'x'.
                opponentBoard.updateCell(row, col, 'x');
                result = 'x';
                MessageFormatter.printBoxedMessage("Hit!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // Handle interruption if needed.
                }
            }
        } else {
            // If no ship is present, mark the cell as a miss with '*'.
            opponentBoard.updateCell(row, col, '*');
            result = '*';
            MessageFormatter.printBoxedMessage("Miss!");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // Handle interruption if needed.
            }
        }

        // Create a Move object to record this shot.
        Move move = new Move(this, new Coordinate(row, col), result);

        // If the shot sunk a ship, record the sunk ship's information (name and coordinates)
        // for use in replay functionality.
        if (result == 'X') {
            move.setSunkShipInfo(cell.getShip().getName(),
                    new ArrayList<>(cell.getShip().getCoordinates()));
        }

        // Return the move.
        return move;
    }
}