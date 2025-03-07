package dev.oleksii;
/**
 * Represents a coordinate on the game board defined by a row and a column.
 * Being a record, Coordinate is immutable and automatically generates
 * accessor methods, as well as implementations for equals, hashCode, and toString.
 */
public record Coordinate(int row, int col) {
}