package dev.oleksii;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DummyPlayer extends Player {
    public DummyPlayer(String name) {
        super(name);
    }
    @Override
    public void placeShips(Scanner scanner) {}
    @Override
    public Move takeTurn(Scanner scanner, Player opponent) {
        return null;
    }
}

class MoveTest {

    @Test
    void setSunkShipInfo() {
        DummyPlayer player = new DummyPlayer("Player");
        Coordinate coordinate = new Coordinate(0, 0);
        Move move = new Move(player, coordinate, 'X');

        List<Coordinate> sunkCoords = Arrays.asList(
                new Coordinate(0, 0),
                new Coordinate(0, 1),
                new Coordinate(0, 2)
        );

        move.setSunkShipInfo("Destroyer", sunkCoords);

        assertThat(move.getSunkShipName()).isEqualTo("Destroyer");
        assertThat(move.getSunkShipCoords()).isEqualTo(sunkCoords);
    }

    @Test
    void isSunk() {
        DummyPlayer player = new DummyPlayer("Player");
        Coordinate coordinate = new Coordinate(1, 1);
        Move moveSunk = new Move(player, coordinate, 'X');

        assertTrue(moveSunk.isSunk());

        Move moveHit = new Move(player, coordinate, 'x');

        assertFalse(moveHit.isSunk());

        Move moveMiss = new Move(player, coordinate, '*');

        assertFalse(moveMiss.isSunk());
    }

    @Test
    void getPlayer() {
        DummyPlayer player = new DummyPlayer("Player");
        Coordinate coordinate = new Coordinate(2, 2);
        Move move = new Move(player, coordinate, 'x');

        assertThat(move.getPlayer()).isEqualTo(player);
    }

    @Test
    void getCoordinate() {
        DummyPlayer player = new DummyPlayer("Player");
        Coordinate coordinate = new Coordinate(3, 4);
        Move move = new Move(player, coordinate, '*');

        assertThat(move.getCoordinate()).isEqualTo(coordinate);
    }

    @Test
    void getResult() {
        DummyPlayer player = new DummyPlayer("Player");
        Coordinate coordinate = new Coordinate(5, 5);
        Move move = new Move(player, coordinate, 'x');

        assertThat(move.getResult()).isEqualTo('x');
    }

    @Test
    void getSunkShipName() {
        DummyPlayer player = new DummyPlayer("Player");
        Coordinate coordinate = new Coordinate(0, 0);
        Move move = new Move(player, coordinate, 'X');

        assertNull(move.getSunkShipName());

        move.setSunkShipInfo("Submarine", null);

        assertThat(move.getSunkShipName()).isEqualTo("Submarine");
    }

    @Test
    void getSunkShipCoords() {
        DummyPlayer player = new DummyPlayer("Player");
        Coordinate coordinate = new Coordinate(0, 0);
        Move move = new Move(player, coordinate, 'X');

        assertNull(move.getSunkShipCoords());

        List<Coordinate> coords = Arrays.asList(new Coordinate(1, 1), new Coordinate(1, 2));

        move.setSunkShipInfo("Submarine", coords);

        assertThat(move.getSunkShipCoords()).isEqualTo(coords);
    }
}
