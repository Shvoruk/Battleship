package dev.oleksii;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game board, handling both the visual display and the underlying game state.
 */
public class Board {

    private final int size; // Board dimension (size x size)
    char[][] displayBoard; // 2D array for the visual display of the board
    private final Map<Coordinate, Cell> gameState; // Maps each board coordinate to its cell state

    /**
     * Constructs a Board of a given size.
     *
     * @param size the dimension of the board (size x size)
     */
    public Board(int size) {
        this.size = size;
        this.displayBoard = new char[size][size];
        this.gameState = new HashMap<>();
        initialise();
    }

    /**
     * Initializes the board:
     * - Fills the display board with '-' (empty cells)
     * - Creates a new Cell for each coordinate in the game state map
     */
    private void initialise() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                displayBoard[row][col] = '-';
                gameState.put(new Coordinate(row, col), new Cell());
            }
        }
    }

    /**
     * Clears any ship markers from the display board by refilling it with '-'.
     */
    public void clearShipsFromDisplay() {
        for (char[] chars : displayBoard) {
            java.util.Arrays.fill(chars, '-');
        }
    }

    /**
     * Checks if a ship can be placed at the specified starting coordinate.
     *
     * @param row        starting row index
     * @param col        starting column index
     * @param shipSize   size (length) of the ship
     * @param horizontal if true, the ship is placed horizontally; otherwise vertically
     * @param verbose    if true, prints an error message on failure
     * @return true if placement is valid; false otherwise
     */
    public boolean canPlaceShip(int row, int col, int shipSize, boolean horizontal, boolean verbose) {
        if (horizontal) {
            // Check horizontal boundaries
            if (col + shipSize > displayBoard[0].length) {
                if (verbose) {
                    System.out.println("Ship placement out of horizontal bounds.");
                }
                return false;
            }
            // Check if any cell in the intended placement already has a ship
            for (int i = 0; i < shipSize; i++) {
                if (displayBoard[row][col + i] == 'S') {
                    if (verbose) {
                        System.out.println("A ship already occupies position: "
                                           + (char) (row + 'A') + (col + i + 1));
                    }
                    return false;
                }
            }
        } else {
            // Check vertical boundaries
            if (row + shipSize > displayBoard.length) {
                if (verbose) {
                    System.out.println("Ship placement out of vertical bounds.");
                }
                return false;
            }
            // Check if any cell in the intended placement already has a ship
            for (int i = 0; i < shipSize; i++) {
                if (displayBoard[row + i][col] == 'S') {
                    if (verbose) {
                        System.out.println("A ship already occupies position: "
                                           + (char) (row + i + 'A') + (col + 1));
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Places a ship on the board if possible.
     *
     * @param ship       the ship to be placed
     * @param row        starting row index for the ship
     * @param col        starting column index for the ship
     * @param horizontal if true, place ship horizontally; otherwise vertically
     * @param verbose    if true, prints error messages if placement fails
     * @return true if the ship is successfully placed; false otherwise
     */
    public boolean placeShip(Ship ship, int row, int col, boolean horizontal, boolean verbose) {
        if (!canPlaceShip(row, col, ship.getSize(), horizontal, verbose)) {
            return false;
        }
        // Set the ship's coordinates (records its placement)
        ship.setCoordinates(row, col, horizontal);

        // Mark each coordinate of the ship on the display board and update game state
        for (Coordinate c : ship.getCoordinates()) {
            displayBoard[c.row()][c.col()] = 'S';
            gameState.get(c).setShip(ship);
        }
        return true;
    }

    /**
     * Updates the board cell at the given coordinate.
     *
     * @param row    row index of the cell to update
     * @param col    column index of the cell to update
     * @param symbol character symbol to set (e.g., 'X' for hit, 'O' for miss)
     * @throws IllegalArgumentException if the provided coordinates are out of bounds
     */
    public void updateCell(int row, int col, char symbol) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Cell coordinates out of bounds!");
        }
        displayBoard[row][col] = symbol;
        gameState.get(new Coordinate(row, col)).setHit(true);
    }

    /**
     * Displays a single board with a bounding box (no centering).
     *
     * @param playerName the name of the player whose board is being displayed
     */
    public void display(String playerName) {
        List<String> lines = new ArrayList<>();
        int boardWidth = (size * 3) + 4; // Calculate box width based on board size

        // Build top and bottom borders
        String topBorder = "╔" + "═".repeat(boardWidth) + "╗";
        String bottomBorder = "╚" + "═".repeat(boardWidth) + "╝";
        lines.add(topBorder);

        // Create a name row (e.g., "Alice's Board") left-aligned in the available space
        String nameRow = String.format("║ %-"+(boardWidth - 2)+"s ║", playerName + "'s Board");
        lines.add(nameRow);

        // Add a horizontal separator after the name row
        String separator = "╠" + "═".repeat(boardWidth) + "╣";
        lines.add(separator);

        // Build the header row with column numbers
        StringBuilder header = new StringBuilder("║    ");
        for (int col = 1; col <= size; col++) {
            header.append(String.format("%-2d ", col));
        }
        header.append("║");
        // Convert the StringBuilder to a String before adding to the list
        lines.add(header.toString());

        // Build each row: letter label followed by the cell values
        for (int row = 0; row < size; row++) {
            StringBuilder sb = new StringBuilder();
            sb.append("║ ").append((char) ('A' + row)).append("  ");
            for (int col = 0; col < size; col++) {
                sb.append(displayBoard[row][col]).append("  ");
            }
            sb.append("║");
            lines.add(sb.toString());
        }

        // Append the bottom border
        lines.add(bottomBorder);

        // Print the entire board line by line
        for (String line : lines) {
            System.out.println(line);
        }
    }

    /**
     * Displays two boards side by side within bounding boxes (no centering applied).
     *
     * @param board1 the first board
     * @param name1  the name for the first board
     * @param board2 the second board
     * @param name2  the name for the second board
     */
    public static void displayBoards(Board board1, String name1,
                                     Board board2, String name2) {
        int size = board1.getSize();
        int boardWidth = (size * 3) + 4; // Calculate each board's box width

        // Build the combined borders for the two boards
        String topBorder = "╔" + "═".repeat(boardWidth) + "╦" + "═".repeat(boardWidth) + "╗";
        String separator = "╠" + "═".repeat(boardWidth) + "╬" + "═".repeat(boardWidth) + "╣";
        String bottomBorder = "╚" + "═".repeat(boardWidth) + "╩" + "═".repeat(boardWidth) + "╝";

        List<String> lines = new ArrayList<>();
        lines.add(topBorder);

        // Create the title row with both board names, padded to align
        String titleRow = String.format("║ %-"+(boardWidth - 2)+"s ║ %-"+(boardWidth - 2)+"s ║",
                name1, name2);
        lines.add(titleRow);
        lines.add(separator);

        // Build the column headers for both boards
        StringBuilder colRow = new StringBuilder();
        colRow.append("║    ");
        for (int i = 1; i <= size; i++) {
            colRow.append(String.format("%-2d ", i));
        }
        colRow.append("║    ");
        for (int i = 1; i <= size; i++) {
            colRow.append(String.format("%-2d ", i));
        }
        colRow.append("║");
        lines.add(colRow.toString());

        // Build and combine rows for both boards
        for (int row = 0; row < size; row++) {
            // Build the left board's row
            StringBuilder rowLeft = new StringBuilder();
            rowLeft.append("║ ").append((char) ('A' + row)).append("  ");
            for (int col = 0; col < size; col++) {
                rowLeft.append(board1.displayBoard[row][col]).append("  ");
            }
            rowLeft.append("║");

            // Build the right board's row
            StringBuilder rowRight = new StringBuilder();
            rowRight.append(" ").append((char) ('A' + row)).append("  ");
            for (int col = 0; col < size; col++) {
                rowRight.append(board2.displayBoard[row][col]).append("  ");
            }
            rowRight.append("║");

            // Combine the left and right rows.
            // Note: Removed the unnecessary toString() call on rowRight because the + operator will invoke it automatically.
            lines.add(rowLeft.toString() + rowRight);
        }

        lines.add(bottomBorder);

        // Print all lines of the combined boards
        for (String line : lines) {
            System.out.println(line);
        }
    }

    /**
     * Reveals hidden ships on the board by updating the display board.
     * Only cells with ships that have not been hit are revealed.
     */
    public void revealShips() {
        gameState.forEach((coordinate, cell) -> {
            // If the cell contains a ship and hasn't been hit, reveal it on the display board.
            if (cell.hasShip() && !cell.isHit()) {
                displayBoard[coordinate.row()][coordinate.col()] = 'S';
            }
        });
    }

    /**
     * @return the board size.
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the game state mapping of coordinates to cell states.
     */
    public Map<Coordinate, Cell> getGameState() {
        return gameState;
    }
}