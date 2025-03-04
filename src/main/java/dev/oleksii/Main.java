package dev.oleksii;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            displayHeader();
            displayMenu();

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Game.start();
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("Exiting the game...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    public static void displayHeader() {
        System.out.println("""
                ██████╗  █████╗ ████████╗████████╗██╗     ███████╗███████╗██╗  ██╗██╗██████╗\s
                ██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║     ██╔════╝██╔════╝██║  ██║██║██╔══██╗\s
                ██████╔╝███████║   ██║      ██║   ██║     █████╗  ███████╗███████║██║██████╔╝
                ██╔══██╗██╔══██║   ██║      ██║   ██║     ██╔══╝  ╚════██║██╔══██║██║██╔═══╝\s
                ██████╔╝██║  ██║   ██║      ██║   ███████╗███████╗███████║██║  ██║██║██║\s
                ╚═════╝ ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝\s
                """);
    }

    public static void displayMenu() {
        System.out.println("""
                ╔══════════════════════════════════════════╗
                ║         Please choose an option:         ║
                ╠══════════════════════════════════════════╣
                ║         1. Start New Game                ║
                ║         2. Watch Previous Game           ║
                ║         3. Exit                          ║
                ╚══════════════════════════════════════════╝
                """);
    }
}