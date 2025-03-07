package dev.oleksii;

import java.util.HashSet;
import java.util.Set;

public class Ship {
    // The ship's name (e.g., "Carrier", "Battleship")
    private final String NAME;
    // The ship's size, representing how many grid cells it occupies
    private final int SIZE;
    // The set of coordinates where the ship is located on the board
    private Set<Coordinate> coordinates;
    // The set of coordinates that have been hit on this ship
    private Set<Coordinate> hits;

    /**
     * Constructs a Ship with a given name and size.
     *
     * @param name The name of the ship.
     * @param size The number of grid cells the ship occupies.
     */
    public Ship(String name, int size) {
        this.NAME = name;
        this.SIZE = size;
        // Initialize the sets for coordinates and hits
        coordinates = new HashSet<>();
        hits = new HashSet<>();
    }

    /**
     * Checks if the provided guess (row, col) hits the ship.
     * If the guessed coordinate is part of the ship, it is recorded as a hit.
     *
     * @param row The row index of the guess.
     * @param col The column index of the guess.
     */
    public void checkHit(int row, int col) {
        // Create a Coordinate instance from the guess
        Coordinate guess = new Coordinate(row, col);
        // If the ship's coordinates contain this guess, record it as a hit
        if (coordinates.contains(guess)) {
            hits.add(guess);
        }
    }

    /**
     * Sets the ship's coordinates on the board based on the starting position and orientation.
     * This method calculates and adds all coordinates the ship occupies.
     *
     * @param row        The starting row index.
     * @param col        The starting column index.
     * @param horizontal True if the ship is placed horizontally; false if vertically.
     */
    public void setCoordinates(int row, int col, boolean horizontal) {
        // Clear any existing coordinates (optional, if repositioning is allowed)
        coordinates.clear();
        // Calculate and add each coordinate based on the ship's size and orientation.
        for (int i = 0; i < SIZE; i++) {
            if (horizontal) {
                // For horizontal placement, increase column index
                coordinates.add(new Coordinate(row, col + i));
            } else {
                // For vertical placement, increase row index
                coordinates.add(new Coordinate(row + i, col));
            }
        }
    }

    /**
     * Determines if the ship is sunk.
     * A ship is considered sunk if the number of hits equals its size.
     *
     * @return True if the ship is sunk; false otherwise.
     */
    public boolean isSunk() {
        return hits.size() == SIZE;
    }

    /**
     * @return The ship's name.
     */
    public String getName() {
        return NAME;
    }

    /**
     * @return The number of cells the ship occupies.
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * @return A set of coordinates where the ship is placed.
     */
    public Set<Coordinate> getCoordinates() {
        return coordinates;
    }
}
