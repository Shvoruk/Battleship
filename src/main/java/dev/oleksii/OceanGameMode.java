package dev.oleksii;

import java.util.ArrayList;
import java.util.List;

/**
 * OceanGameMode defines a more challenging game mode with a larger board and twice the number of ships.
 * It creates a 20x20 board and includes the following ships:
 * - 2 Carriers (each size 5)
 * - 2 Battleships (each size 4)
 * - 2 Cruisers (each size 3)
 * - 2 Submarines (each size 3)
 * - 2 Destroyers (each size 2)
 */
public class OceanGameMode extends GameMode {
    public OceanGameMode() {
        // Create a list to hold the ship objects for this mode.
        List<Ship> ships = new ArrayList<>();

        // Add the set of ships for Ocean mode. This mode has double the ships compared to Sea mode.
        ships.add(new Ship("Carrier", 5));
        ships.add(new Ship("Carrier", 5));
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Destroyer", 2));
        ships.add(new Ship("Destroyer", 2));

        // Set the board size to 20 (i.e., a 20x20 grid).
        setBoardSize(20);
        // Set the list of ships for this game mode.
        setShips(ships);
    }
}