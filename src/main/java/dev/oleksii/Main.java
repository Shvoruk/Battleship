package dev.oleksii;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Battleship Game!");
        char[][] board1 = new char[10][10];
        char[][] board2 = new char[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board1[i][j] = '-';
                board2[i][j] = '-';
            }
        }

        System.out.println("  Player 1                         Player 2");
        System.out.println();

        System.out.println("  1 2 3 4 5 6 7 8 9 10             1 2 3 4 5 6 7 8 9 10");

        for (int i = 0; i < 10; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(board1[i][j] + " ");
            }
            System.out.print("         ");
            System.out.print("  " + (char) ('A' + i) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(board2[i][j] + " ");
            }
            System.out.println();
        }
    }
}