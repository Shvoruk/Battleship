package dev.oleksii;

public class DisplayBoard {
    public static void displayBoards(int size, String player1, String player2) {
        // Create two boards dynamically
        char[][] board1 = new char[size][size];
        char[][] board2 = new char[size][size];

        // Fill boards with '-' as default values
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board1[i][j] = '-';
                board2[i][j] = '-';
            }
        }

        // Calculate dynamic width for board borders
        int boardWidth = (size * 3) + 4; // 2 chars per column + spacing + padding
        String topBorder = "╔" + "═".repeat(boardWidth) + "╦" + "═".repeat(boardWidth) + "╗";
        String separator = "╠" + "═".repeat(boardWidth) + "╬" + "═".repeat(boardWidth) + "╣";
        String bottomBorder = "╚" + "═".repeat(boardWidth) + "╩" + "═".repeat(boardWidth) + "╝";

        // Display the top border
        System.out.println(topBorder);

        // Display title row
        System.out.printf("║ %-"+ (boardWidth - 2) +"s ║ %-"+ (boardWidth - 2) +"s ║%n", player1, player2);

        // Separator between title and column numbers
        System.out.println(separator);

        // Print column numbers for both boards
        System.out.print("║    "); // Padding before numbers
        for (int i = 1; i <= size; i++) {
            System.out.printf("%-2d ", i); // Print column number
        }
        System.out.print("║    "); // Space between boards
        for (int i = 1; i <= size; i++) {
            System.out.printf("%-2d ", i);
        }
        System.out.println("║");

        // Print the main board content with row labels
        for (int i = 0; i < size; i++) {
            System.out.print("║ " + (char) ('A' + i) + "  "); // Row label for first board
            for (int j = 0; j < size; j++) {
                System.out.print(board1[i][j] + "  ");
            }
            System.out.print("║ " + (char) ('A' + i) + "  "); // Row label for second board
            for (int j = 0; j < size; j++) {
                System.out.print(board2[i][j] + "  ");
            }
            System.out.println("║");
        }

        // Print the bottom border
        System.out.println(bottomBorder);
    }
}
