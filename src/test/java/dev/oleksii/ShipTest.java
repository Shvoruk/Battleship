package dev.oleksii;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipTest {

    @Test
    void checkHit() {
        Ship ship = new Ship("Cruiser", 3);
        ship.setCoordinates(1, 1, true);

        assertThat(ship.isSunk()).isFalse();

        ship.checkHit(1, 1);

        assertThat(ship.isSunk()).isFalse();


        ship.checkHit(0, 0);

        assertThat(ship.isSunk()).isFalse();
    }

    @Test
    void setCoordinates() {
        Ship ship = new Ship("Destroyer", 3);
        ship.setCoordinates(2, 3, true);

        Set<Coordinate> coords = ship.getCoordinates();
        assertEquals(3, coords.size(), "Ship should occupy 3 cells");

        assertTrue(coords.contains(new Coordinate(2, 3)));
        assertTrue(coords.contains(new Coordinate(2, 4)));
        assertTrue(coords.contains(new Coordinate(2, 5)));

        ship.setCoordinates(2, 3, false);
        coords = ship.getCoordinates();
        assertEquals(3, coords.size(), "Ship should occupy 3 cells");

        assertTrue(coords.contains(new Coordinate(2, 3)));
        assertTrue(coords.contains(new Coordinate(3, 3)));
        assertTrue(coords.contains(new Coordinate(4, 3)));
    }

    @Test
    void isSunk() {
        Ship ship = new Ship("Cruiser", 3);

        ship.setCoordinates(1, 1, true);

        ship.checkHit(1, 1);
        ship.checkHit(1, 2);
        ship.checkHit(1, 3);

        assertThat(ship.isSunk()).isTrue();
    }

    @Test
    void getName() {
        Ship ship = new Ship("Submarine", 2);

        assertThat(ship.getName()).isEqualTo("Submarine");
    }

    @Test
    void getSize() {
        Ship ship = new Ship("Battleship", 4);

        assertThat(ship.getSize()).isEqualTo(4);
    }

    @Test
    void getCoordinates() {
        Ship ship = new Ship("Destroyer", 3);

        assertThat(ship.getCoordinates()).isEmpty();

        ship.setCoordinates(3, 4, true);

        Set<Coordinate> coords = ship.getCoordinates();
        assertThat(coords).hasSize(3);
        assertThat(coords).contains(new Coordinate(3, 4), new Coordinate(3, 5), new Coordinate(3, 6));
    }
}
