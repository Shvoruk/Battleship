package dev.oleksii;

import java.util.List;

/**
 * Abstract base class representing a game mode.
 * A GameMode defines the board size and the set of ships used for that mode.
 */
public abstract class GameMode {
    // The size of the game board (e.g., 10 for a 10x10 grid).
    private int boardSize;

    // The list of ships (prototypes) used in this game mode.
    private List<Ship> ships;

    /**
     * Returns the size of the board for this game mode.
     *
     * @return the board size.
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Sets the size of the board for this game mode.
     *
     * @param boardSize the board size to set.
     */
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    /**
     * Returns the list of ships associated with this game mode.
     *
     * @return the list of ships.
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Sets the list of ships for this game mode.
     *
     * @param ships the list of ships to set.
     */
    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}