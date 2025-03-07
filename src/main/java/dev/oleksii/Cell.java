package dev.oleksii;

/**
 * Represents a single cell on the game board.
 * A cell can contain a part of a ship and track whether it has been hit.
 */
public class Cell {
    // Indicates whether this cell contains a ship.
    private boolean hasShip;
    // Indicates whether this cell has been hit.
    private boolean isHit;
    // Reference to the ship that occupies this cell (if any).
    private Ship ship;

    /**
     * Constructs an empty cell with no ship and not hit.
     */
    public Cell() {
        this.hasShip = false;
        this.isHit = false;
    }

    /**
     * Checks if this cell contains a ship.
     *
     * @return true if a ship is present, false otherwise.
     */
    public boolean hasShip() {
        return hasShip;
    }

    /**
     * Checks if this cell has been hit.
     *
     * @return true if the cell has been hit, false otherwise.
     */
    public boolean isHit() {
        return isHit;
    }

    /**
     * Sets the hit status for this cell.
     *
     * @param hit true to mark the cell as hit, false otherwise.
     */
    public void setHit(boolean hit) {
        this.isHit = hit;
    }

    /**
     * Gets the ship occupying this cell.
     *
     * @return the ship if one is present, or null if the cell is empty.
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Sets the ship for this cell.
     * Also marks the cell as containing a ship.
     *
     * @param ship the ship to place in this cell.
     */
    public void setShip(Ship ship) {
        this.ship = ship;
        this.hasShip = true;
    }
}