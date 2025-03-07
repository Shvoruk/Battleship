package dev.oleksii;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The Game class manages the overall game play, including player setup, turn-taking,
 * move processing, win checking, and replay functionality.
 */
public class Game {
    // The two players in the game.
    protected Player player1;
    protected Player player2;

    // GameMode contains information like board size and ship configuration.
    protected GameMode mode;

    // List to store all the moves made during the game.
    protected List<Move> gameMoves;

    // Stores the eventual winner. If null, no winner has been determined yet.
    private Player winner = null;

    /**
     * Constructs a Game with the specified mode and two players.
     * Also, it initializes each player's board by calling their setup method.
     *
     * @param mode    the game mode (defines board size and ship configuration)
     * @param player1 the first player
     * @param player2 the second player
     * @param scanner a Scanner object for user input during setup
     */
    public Game(GameMode mode, Player player1, Player player2, Scanner scanner) {
        this.mode = mode;
        this.player1 = player1;
        this.player2 = player2;
        // Each player sets up their board and places their ships.
        player1.setup(mode, scanner);
        player2.setup(mode, scanner);
        // Initialize the move history list.
        gameMoves = new ArrayList<>();
    }

    /**
     * Manages the main game loop where players take turns until one wins.
     *
     * @param scanner a Scanner object for reading user input during play
     */
    public void play(Scanner scanner) {
        // Randomly choose which player goes first.
        boolean player1Turn = new Random().nextBoolean();

        // Game loop continues until a win condition is met.
        while (true) {
            // Clear the screen for a fresh display.
            MainMenu.clearScreen();

            // Determine current player and opponent based on turn.
            Player current = player1Turn ? player1 : player2;
            Player opponent = player1Turn ? player2 : player1;

            // Display both players' boards side by side.
            Board.displayBoards(player1.getBoard(), player1.getName(),
                    player2.getBoard(), player2.getName());
            // Show whose turn it is.
            MessageFormatter.printBoxedMessage(current.getName() + "'s turn to shoot!");

            // Current player takes a shot against the opponent.
            Move move = current.takeTurn(scanner, opponent);
            // Record the move in the current player's history.
            current.addMove(move);
            // Also record the move in the overall game move history.
            gameMoves.add(move);

            // Check if the opponent has lost (i.e., all ships sunk).
            if (checkWin(opponent)) {
                // Current player wins.
                this.winner = current;  // Store the winner.

                // Reveal any un-hit ship parts on both boards.
                player1.getBoard().revealShips();
                player2.getBoard().revealShips();

                // Refresh the screen and display the final state.
                MainMenu.clearScreen();
                Board.displayBoards(player1.getBoard(), player1.getName(),
                        player2.getBoard(), player2.getName());

                // Congratulate the winner.
                MessageFormatter.printBoxedMessage(
                        "Congratulations " + current.getName() + ", you won!"
                );
                System.out.println("Press Enter to continue...");
                scanner.nextLine();  // Wait for the player to press Enter.
                return;  // End the game loop.
            }

            // Determine if the move was a hit (using specific characters to represent a hit).
            char r = move.getResult();
            boolean isHit = (r == 'x' || r == 'X' || r == 'H');
            // If the move was not a hit, switch turns.
            if (!isHit) {
                player1Turn = !player1Turn;
            }
        }
    }

    /**
     * Checks if all ships of the opponent have been sunk.
     *
     * @param opponent the player whose ships are being checked
     * @return true if all ships are sunk, false otherwise
     */
    protected boolean checkWin(Player opponent) {
        for (Ship ship : opponent.getShips()) {
            // If any ship is not sunk, the game is still ongoing.
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Applies a move to a given board by updating the cell.
     * If the move resulted in a sunk ship, all coordinates of that ship are marked.
     *
     * @param board the board to update
     * @param move  the move to apply
     */
    private void applyMoveToBoard(Board board, Move move) {
        if (move.isSunk()) {
            // For a sunk ship, mark all the ship's coordinates with 'X'.
            for (Coordinate c : move.getSunkShipCoords()) {
                board.updateCell(c.row(), c.col(), 'X');
            }
        } else {
            // Otherwise, update the single coordinate with the move's result.
            Coordinate c = move.getCoordinate();
            board.updateCell(c.row(), c.col(), move.getResult());
        }
    }

    /**
     * Replays the game move-by-move.
     * Displays the board after each move and allows the user to step through the moves.
     */
    public void replayGame() {
        // Create fresh boards for the replay (to avoid altering the original boards).
        Board replayBoard1 = new Board(mode.getBoardSize());
        Board replayBoard2 = new Board(mode.getBoardSize());

        Scanner replayScanner = new Scanner(System.in);

        // Iterate over each move in the game.
        for (Move move : gameMoves) {
            // 1) Retrieve the player's name and convert the move's coordinate to a string.
            String playerName = move.getPlayer().getName();
            String stringCoordinate = coordinateToString(move.getCoordinate());

            // 2) Apply the move to the OPPOSITE board:
            // If player1 made the move, apply it to player2's replay board, and vice versa.
            if (move.getPlayer() == player1) {
                applyMoveToBoard(replayBoard2, move);
            } else {
                applyMoveToBoard(replayBoard1, move);
            }

            // Display a message indicating the move.
            MessageFormatter.printBoxedMessage(playerName + " guessed " + stringCoordinate);

            // Display the current state of both boards.
            Board.displayBoards(replayBoard1, player1.getName(),
                    replayBoard2, player2.getName());

            // If the move resulted in a sunk ship, display that information.
            if (move.isSunk()) {
                MessageFormatter.printBoxedMessage(move.getSunkShipName() + " sunk!");
            }

            // Prompt the user to press Enter to proceed to the next move,
            // or input 'B' to go back to the replay menu.
            System.out.print("(Enter = next move, B = back to replay menu): ");
            String userInput = replayScanner.nextLine().trim().toUpperCase();

            if (userInput.equals("B")) {
                // Exit the replay loop and return to the replay menu.
                MessageFormatter.printBoxedMessage("Returning to Replay Menu...");
                return;
            }
        }

        // After all moves, reveal any ships that remain hidden.
        replayBoard1.revealShips();
        replayBoard2.revealShips();

        // Display the final boards with all ships revealed.
        MessageFormatter.printBoxedMessage("Final Boards with all ships revealed:");
        Board.displayBoards(replayBoard1, player1.getName(),
                replayBoard2, player2.getName());

        // Announce the winner if one exists.
        if (winner != null) {
            MessageFormatter.printBoxedMessage("Winner was " + winner.getName() + "!");
        } else {
            MessageFormatter.printBoxedMessage("No winner information available!");
        }

        // Close the scanner used for replay.
        replayScanner.close();
    }

    /**
     * Converts a Coordinate object to a user-friendly string.
     * For example, a coordinate (0, 4) becomes "A5".
     *
     * @param coordinate the coordinate to convert
     * @return the string representation of the coordinate
     */
    private static String coordinateToString(Coordinate coordinate) {
        // Convert the row number to a corresponding uppercase letter.
        char letter = (char) ('A' + coordinate.row());
        // Convert the column index to a 1-based number.
        int number = coordinate.col() + 1;
        // Concatenate letter and number to form the coordinate string.
        return letter + String.valueOf(number);
    }
}