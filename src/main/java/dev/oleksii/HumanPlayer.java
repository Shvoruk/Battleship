package dev.oleksii;

import java.util.Scanner;

/**
 * HumanPlayer is a concrete subclass of Player.
 * It implements the methods for placing ships and taking turns using console input.
 */
public class HumanPlayer extends Player {

    /**
     * Constructs a HumanPlayer with the given name.
     *
     * @param name the name of the player.
     */
    public HumanPlayer(String name) {
        super(name); // Call the base class constructor to set the player's name.
    }

    /**
     * Allows the human player to place ships on the board.
     * This method loops through all the ships that the player owns and prompts the user
     * to specify the starting coordinate and orientation for each ship.
     *
     * @param scanner a Scanner object for reading user input.
     */
    @Override
    public void placeShips(Scanner scanner) {
        // Get the board size (e.g., 10, 20, etc.) from the player's board.
        int boardSize = getBoard().getSize();

        // Iterate over each ship that must be placed.
        for (Ship ship : getShips()) {
            boolean placed = false;
            // Continue prompting until the ship is placed successfully.
            while (!placed) {
                // Clear the screen for clarity.
                MainMenu.clearScreen();
                System.out.println();
                // Display the current state of the board with the player's name.
                getBoard().display(this.getName());

                // Prompt the player to place the current ship.
                MessageFormatter.printBoxedMessage("Place your " + ship.getName() + " (size " + ship.getSize() + ")");

                int row, col;

                // Loop until a valid starting coordinate is entered.
                while (true) {
                    System.out.print("Enter starting coordinate (e.g., A5): ");
                    // Read the input, trim whitespace, and convert it to uppercase.
                    String input = scanner.nextLine().trim().toUpperCase();

                    // Check that the input has at least 2 characters (e.g., "A5").
                    if (input.length() < 2) {
                        MessageFormatter.printBoxedMessage("Invalid format. Must be letter + number (e.g. A5).");
                        continue;
                    }

                    // Extract the letter (row indicator) and convert it to a row index.
                    char letter = input.charAt(0);
                    row = letter - 'A';

                    // Validate that the row index is within the bounds of the board.
                    if (row < 0 || row >= boardSize) {
                        MessageFormatter.printBoxedMessage("Row out of range (must be between A and "
                                                           + (char)('A' + boardSize - 1) + ").");
                        continue;
                    }

                    // Extract the column part from the input string.
                    String colPart = input.substring(1); // e.g., "5" from "A5"
                    try {
                        // Parse the column number and convert it to a zero-based index.
                        col = Integer.parseInt(colPart) - 1;
                    } catch (NumberFormatException e) {
                        MessageFormatter.printBoxedMessage("Invalid number format. Try again.");
                        continue;
                    }

                    // Validate that the column index is within the board range.
                    if (col < 0 || col >= boardSize) {
                        MessageFormatter.printBoxedMessage(
                                "Column out of range (must be between 1 and " + boardSize + ")."
                        );
                        continue;
                    }

                    // If row and column are valid, exit the inner loop.
                    break;
                }

                // -------------------------------
                // 2) GET ORIENTATION
                // -------------------------------
                boolean horizontal;
                // Loop until a valid orientation is provided.
                while (true) {
                    System.out.print("Enter orientation (H for horizontal, V for vertical): ");
                    String orient = scanner.nextLine().trim().toUpperCase();

                    // Set orientation based on user input.
                    if (orient.equals("H")) {
                        horizontal = true;
                        break;
                    } else if (orient.equals("V")) {
                        horizontal = false;
                        break;
                    } else {
                        MessageFormatter.printBoxedMessage("Invalid orientation. Please enter H or V.");
                    }
                }

                // Attempt to place the ship on the board using the specified coordinate and orientation.
                if (getBoard().placeShip(ship, row, col, horizontal, true)) {
                    placed = true; // Placement was successful.
                    MessageFormatter.printBoxedMessage(ship.getName() + " placed successfully.");
                } else {
                    MessageFormatter.printBoxedMessage("Invalid placement, try again.");
                }
                // Pause briefly so the player can see the message.
                try { Thread.sleep(1000); } catch (InterruptedException ignored) { }
            }
        }
    }

    /**
     * Allows the human player to take a turn by guessing a coordinate on the opponent's board.
     * It validates the input coordinate, ensures that the cell hasn't been targeted before,
     * then processes the move and returns a Move object representing the result.
     *
     * @param scanner  a Scanner object for reading user input.
     * @param opponent the opposing player whose board is targeted.
     * @return a Move object representing the shot taken.
     */
    @Override
    public Move takeTurn(Scanner scanner, Player opponent) {
        // Get the board size from the opponent's board.
        int boardSize = opponent.getBoard().getSize();
        int row, col;

        // Loop until a valid coordinate is entered.
        while (true) {
            System.out.print("Enter your guess: ");
            String input = scanner.nextLine().trim().toUpperCase();

            // Validate input format: at least 2 characters and must start with a letter.
            if (input.length() < 2 || !Character.isLetter(input.charAt(0))) {
                MessageFormatter.printBoxedMessage("Invalid format. Must be letter + number (e.g., A5).");
                continue;
            }

            // Convert the first character (letter) to a row index.
            row = input.charAt(0) - 'A';
            if (row < 0 || row >= boardSize) {
                MessageFormatter.printBoxedMessage("Row out of range. Valid letters: A-" + (char) ('A' + boardSize - 1));
                continue;
            }

            // Convert the numeric part of the input to a column index.
            try {
                col = Integer.parseInt(input.substring(1)) - 1;
            } catch (NumberFormatException e) {
                MessageFormatter.printBoxedMessage("Invalid number format. Try again.");
                continue;
            }
            if (col < 0 || col >= boardSize) {
                MessageFormatter.printBoxedMessage("Column out of range. Valid range: 1-" + boardSize);
                continue;
            }

            // Check whether the chosen cell has already been hit.
            Cell cell = opponent.getBoard().getGameState().get(new Coordinate(row, col));
            if (cell.isHit()) {
                MessageFormatter.printBoxedMessage("This cell has already been targeted. Please choose another cell.");
                continue;
            }

            // If all validations pass, exit the loop.
            break;
        }

        // Retrieve the cell corresponding to the chosen coordinate.
        Cell cell = opponent.getBoard().getGameState().get(new Coordinate(row, col));
        char result;
        // Process the move based on whether a ship occupies that cell.
        if (cell.hasShip()) {
            // Register a hit on the ship at the specified coordinate.
            cell.getShip().checkHit(row, col);

            // If the ship is now sunk, mark all its coordinates with 'X'.
            if (cell.getShip().isSunk()) {
                for (Coordinate c : cell.getShip().getCoordinates()) {
                    opponent.getBoard().updateCell(c.row(), c.col(), 'X');
                }
                MessageFormatter.printBoxedMessage(cell.getShip().getName() + " sunk!");
                result = 'X';
            } else {
                // If the ship is hit but not sunk, mark the cell with a lowercase 'x'.
                opponent.getBoard().updateCell(row, col, 'x');
                result = 'x';
                MessageFormatter.printBoxedMessage("Hit!");
            }
        } else {
            // If no ship is present at the coordinate, mark it as a miss using '*'.
            opponent.getBoard().updateCell(row, col, '*');
            result = '*';
            MessageFormatter.printBoxedMessage("Miss!");
        }
        try {
            // Pause briefly so the player can see the outcome of their move.
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // If the pause is interrupted, simply continue.
        }

        // Create and return a new Move object that records this move, including its result.
        return new Move(this, new Coordinate(row, col), result);
    }
}