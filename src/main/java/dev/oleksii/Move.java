package dev.oleksii;

import java.util.List;

/**
 * Represents a move made by a player during the game.
 * A move contains the player who made it, the targeted coordinate,
 * and the result of the move. If a ship was sunk as a result,
 * additional information about that ship is stored.
 */
public class Move {
    // The player who made the move.
    private final Player player;
    // The coordinate where the move was targeted.
    private final Coordinate coordinate;
    // The result of the move:
    // '*' for a miss, 'x' for a hit, 'X' if the ship was sunk.
    private final char result;
    // If the move results in a sunk ship, store the name of that ship.
    private String sunkShipName;
    // If the move results in a sunk ship, store all its coordinates.
    private List<Coordinate> sunkShipCoords;

    /**
     * Constructs a Move with the specified player, coordinate, and result.
     *
     * @param player     the player making the move.
     * @param coordinate the coordinate targeted by the move.
     * @param result     the result of the move ('*', 'x', or 'X').
     */
    public Move(Player player, Coordinate coordinate, char result) {
        this.player = player;
        this.coordinate = coordinate;
        this.result = result;
    }

    /**
     * Sets additional information for a move that resulted in a sunk ship.
     *
     * @param shipName the name of the sunk ship (e.g., "Battleship").
     * @param coords   the list of coordinates that the sunk ship occupies.
     */
    public void setSunkShipInfo(String shipName, List<Coordinate> coords) {
        this.sunkShipName = shipName;
        this.sunkShipCoords = coords;
    }

    /**
     * Checks if this move resulted in a sunk ship.
     *
     * @return true if the move's result indicates a sunk ship ('X'), false otherwise.
     */
    public boolean isSunk() {
        return result == 'X';
    }

    /**
     * Gets the player who made the move.
     *
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the coordinate targeted by the move.
     *
     * @return the coordinate.
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Gets the result of the move.
     *
     * @return the result character ('*', 'x', or 'X').
     */
    public char getResult() {
        return result;
    }

    /**
     * Gets the name of the sunk ship if this move resulted in sinking a ship.
     *
     * @return the sunk ship's name, or null if no ship was sunk.
     */
    public String getSunkShipName() {
        return sunkShipName;
    }

    /**
     * Gets the list of coordinates for the sunk ship, if applicable.
     *
     * @return the list of coordinates for the sunk ship, or null if no ship was sunk.
     */
    public List<Coordinate> getSunkShipCoords() {
        return sunkShipCoords;
    }
}