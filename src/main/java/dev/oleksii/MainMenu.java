package dev.oleksii;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainMenu {
    // History of games
    private static final List<Game> gameHistory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mainLoop:
        while (true) {
            clearScreen();
            printLogo();

            // Use MessageFormatter for the main prompt
            MessageFormatter.printBoxedMenu(
                    "Please choose an option:",
                    "1. Start New Game",
                    "2. Watch Previous Games",
                    "0. Exit Game"
            );
            System.out.print("Enter your choice: ");
            int choice = getIntInput(scanner);
            switch (choice) {
                case 1 -> newGameMenu(scanner);
                case 2 -> replayMenu(scanner);
                case 0 -> {
                    MessageFormatter.printBoxedMessage("Exiting...");
                    break mainLoop;
                }
                default -> MessageFormatter.printBoxedMessage("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    /**
     * High-level method for starting a new game. It will:
     * 1. Ask the user to choose an opponent (friend or computer).
     * 2. Ask the user to choose a game mode (Sea or Ocean).
     * 3. Create and play the game, then return to main menu.
     */
    private static void newGameMenu(Scanner scanner) {
        while (true) {
            int opponentChoice = chooseOpponent(scanner);
            if (opponentChoice == 0) {
                return;  // back to main menu
            }
            if (opponentChoice < 0) {
                continue; // invalid choice => prompt again
            }

            clearScreen();
            printLogo();
            // Prompt for Player 1's name
            MessageFormatter.printBoxedMessage("What is yor name, Player?");
            System.out.print("Enter name: ");
            String player1Name = scanner.nextLine().trim();
            if (player1Name.isEmpty()) {
                player1Name = "Player I";  // fallback if user just hit Enter
            }

            // If playing with a friend, prompt for Player 2’s name;
            // if playing vs computer, just set a fixed name
            String player2Name;
            if (opponentChoice == 1) {

                clearScreen();
                printLogo();

                MessageFormatter.printBoxedMessage("What is your name, Player II?");
                System.out.print("Enter name: ");
                player2Name = scanner.nextLine().trim();
                if (player2Name.isEmpty()) {
                    player2Name = "Player II";
                }
            } else {
                player2Name = "Computer"; // Always the same, no prompt
            }

            // Now choose a game mode
            GameMode gameMode = chooseGameMode(scanner);
            if (gameMode == null) {
                continue; // user picked 0 => back to Opponent Selection
            }

            // Build the correct players
            Player player1 = new HumanPlayer(player1Name);
            Player player2;
            if (opponentChoice == 1) {
                // human vs human
                player2 = new HumanPlayer(player2Name);
            } else {
                // human vs computer
                player2 = new ComputerPlayer(new Random());
            }

            // Create & play the game
            Game game = new Game(gameMode, player1, player2, scanner);
            game.play(scanner);
            gameHistory.add(game);

            // After the game ends, return to main menu
            return;
        }
    }


    /**
     * Displays a submenu to choose an opponent type:
     * Returns:
     *  0 if user wants to go back,
     *  1 or 2 if valid selection,
     * -1 if invalid input.
     */
    private static int chooseOpponent(Scanner scanner) {
        clearScreen();
        printLogo();

        MessageFormatter.printBoxedMenu(
                "New Game - Choose Opponent",
                "1. Play with a Friend",
                "2. Play with the Computer",
                "0. Back to Main Menu"
        );
        System.out.print("Enter your choice: ");
        int choice = getIntInput(scanner);
        return switch (choice) {
            case 0 -> 0;    // 0 => back to main menu
            case 1 -> 1;    // Play with a Friend
            case 2 -> 2;    // Play with the Computer
            default -> {
                MessageFormatter.printBoxedMessage("Invalid option. Please try again.");
                yield -1;
            }
        };
    }

    /**
     * Displays a submenu to choose a game mode:
     * Returns the chosen GameMode, or null if user chooses 0.
     */
    private static GameMode chooseGameMode(Scanner scanner) {
        clearScreen();
        printLogo();

        MessageFormatter.printBoxedMenu(
                "Select Game Mode",
                "1. Sea (10x10 grid, standard ships)",
                "2. Ocean (20x20 grid, twice the ships)",
                "0. Back to Opponent Selection"
        );
        System.out.print("Enter your choice: ");
        int modeChoice = getIntInput(scanner);
        return switch (modeChoice) {
            case 0 -> null; // user wants to go back
            case 1 -> new SeaGameMode();
            case 2 -> new OceanGameMode();
            default -> {
                MessageFormatter.printBoxedMessage("Invalid mode. Please try again.");
                yield null;
            }
        };
    }

    /**
     * Allows user to list and replay past games.
     */
    private static void replayMenu(Scanner scanner) {
        if (gameHistory.isEmpty()) {
            MessageFormatter.printBoxedMessage("No games have been played yet.");
            try {
                Thread.sleep(3000); // Let user see the message
            } catch (InterruptedException e) {
                // ignore
            }
            return;
        }

        while (true) {
            // Build the list of options (games + "0. Back...")
            List<String> options = new ArrayList<>();
            options.add("Select a game:"); // subheader or instruction
            for (int i = 0; i < gameHistory.size(); i++) {
                options.add((i + 1) + ". Game " + (i + 1));
            }
            options.add("0. Back to Main Menu");

            // Print it all in one box
            MessageFormatter.printBoxedMenu(
                    "Replay Previous Games",
                    options.toArray(new String[0])
            );

            System.out.print("Enter your choice: ");
            // Now read the user's choice
            int choice = getIntInput(scanner);
            if (choice == 0) {
                return; // back to main menu
            }
            if (choice < 1 || choice > gameHistory.size()) {
                MessageFormatter.printBoxedMessage("Invalid choice. Try again.");
                continue;
            }

            gameHistory.get(choice - 1).replayGame();
        }
    }


    /**
     * Safely reads an integer from the user.
     * If the input is invalid, prompts user to try again until a valid integer is entered.
     */
    private static int getIntInput(Scanner scanner) {
        while (true) {
            if (!scanner.hasNextLine()) {
                MessageFormatter.printBoxedMessage("No more input found. Exiting...");
                System.exit(0);
            }

            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                MessageFormatter.printBoxedMessage("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Clears the console screen using ANSI escape codes.
     * May not work on all terminals (particularly Windows cmd without ANSI support).
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void printLogo() {
        System.out.println("""
                ██████╗  █████╗ ████████╗████████╗██╗     ███████╗███████╗██╗  ██╗██╗██████╗
                ██╔══██╗██╔══██╗╚══██╔══╝╚══██╔══╝██║     ██╔════╝██╔════╝██║  ██║██║██╔══██╗
                ██████╔╝███████║   ██║      ██║   ██║     █████╗  ███████╗███████║██║██████╔╝
                ██╔══██╗██╔══██║   ██║      ██║   ██║     ██╔══╝  ╚════██║██╔══██║██║██╔═══╝
                ██████╔╝██║  ██║   ██║      ██║   ███████╗███████╗███████║██║  ██║██║██║
                ╚═════╝ ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝
                """);
    }
}