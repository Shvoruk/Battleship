package dev.oleksii;

import java.util.Objects;

public record Coordinate(int row, int col) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row() == that.row() && col() == that.col();
    }

    @Override
    public int hashCode() {
        return Objects.hash(row(), col());
    }

    @Override
    public String toString() {
        return "Coordinate{" +
               "row=" + row +
               ", col=" + col +
               '}';
    }
}
