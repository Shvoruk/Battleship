package dev.oleksii;

import java.util.ArrayList;
import java.util.List;

/**
 * SeaGameMode defines the standard Battleship game mode.
 * It creates a 10x10 board with the following ships:
 * - 1 Carrier (size 5)
 * - 1 Battleship (size 4)
 * - 1 Cruiser (size 3)
 * - 1 Submarine (size 3)
 * - 1 Destroyer (size 2)
 */
public class SeaGameMode extends GameMode {
    public SeaGameMode() {
        // Create a list to hold the ship objects for this mode.
        List<Ship> ships = new ArrayList<>();

        // Add the standard set of ships for Sea mode.
        ships.add(new Ship("Carrier", 5));
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Destroyer", 2));

        // Set the board size to 10 (i.e., a 10x10 grid).
        setBoardSize(10);
        // Set the list of ships for this game mode.
        setShips(ships);
    }
}