package dev.oleksii;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Abstract base class for all players in the game.
 * This class encapsulates common functionality such as board setup,
 * storing ships, and recording move history.
 */
public abstract class Player {
    // The player's name. This is set in the constructor and never changes.
    private final String name;

    // The board assigned to this player.
    private Board board;

    // The list of ships belonging to player.
    private List<Ship> ships;

    // The history of moves made by this player.
    private final List<Move> moveHistory = new ArrayList<>();

    /**
     * Constructs a Player with the given name.
     *
     * @param name the name of the player.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Sets up the player by initialising their board, creating deep copies of the ships,
     * and calling the abstract ship placement method.
     *
     * @param mode    the game mode which contains board size and ship prototypes.
     * @param scanner the Scanner used for user input.
     */
    public void setup(GameMode mode, Scanner scanner) {
        // Initialise the player's board with the board size from the game mode.
        setBoard(new Board(mode.getBoardSize()));

        // Create deep copies of the ships from the game mode so that each player gets their own set.
        List<Ship> playerShips = new ArrayList<>();
        for (Ship shipPrototype : mode.getShips()) {
            // Create a new Ship instance based on the prototype.
            playerShips.add(new Ship(shipPrototype.getName(), shipPrototype.getSize()));
        }
        setShips(playerShips);

        // Call the abstract method for placing ships on the board.
        placeShips(scanner);

        // After placement, clear the ship markers from the display (if needed for gameplay).
        getBoard().clearShipsFromDisplay();
    }

    /**
     * Abstract method for placing ships on the board.
     * This must be implemented by concrete subclasses (e.g., HumanPlayer, ComputerPlayer).
     *
     * @param scanner the Scanner used for user input.
     */
    public abstract void placeShips(Scanner scanner);

    /**
     * Abstract method for taking a turn (making a move).
     * This must be implemented by concrete subclasses.
     *
     * @param scanner  the Scanner used for user input.
     * @param opponent the opposing player.
     * @return a Move object representing the move made.
     */
    public abstract Move takeTurn(Scanner scanner, Player opponent);

    /**
     * Adds a move to the player's move history.
     *
     * @param move the move to add.
     */
    public void addMove(Move move) {
        moveHistory.add(move);
    }

    /**
     * Returns the list of ships belonging to the player.
     *
     * @return the list of ships.
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Sets the player's ships.
     *
     * @param ships the list of ships to assign.
     */
    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    /**
     * Returns the player's board.
     *
     * @return the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the player's board.
     *
     * @param board the board to assign.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * @return the name of player.
     */
    public String getName() {
        return name;
    }
}