package dev.oleksii;

public class MessageFormatter {

    /**
     * Prints a single-line message in a box with 2 spaces on each side.
     * The box consists of a top border, a middle line containing the message,
     * and a bottom border.
     *
     * @param message the message to display in the box.
     */
    public static void printBoxedMessage(String message) {
        // Set the horizontal padding (2 spaces on the left and right).
        int horizontalPadding = 2;
        // Calculate the total width of the box: message length + padding on both sides.
        int boxWidth = message.length() + (horizontalPadding * 2);

        // Build the top border using the "═" character.
        String topBorder = "╔" + "═".repeat(boxWidth) + "╗";
        // Build the bottom border similarly.
        String bottomBorder = "╚" + "═".repeat(boxWidth) + "╝";

        // Build the middle line:
        // It starts with a left border "║", followed by the left padding,
        // then the message, then the right padding, and ends with a right border "║".
        String middle = "║"
                        + " ".repeat(horizontalPadding)
                        + message
                        + " ".repeat(horizontalPadding)
                        + "║";

        // Print the complete box.
        System.out.println(String.join("\n",
                topBorder,
                middle,
                bottomBorder
        ));

    }

    /**
     * Prints a multi-line menu in a box with a header and multiple option lines.
     * The header is separated from the options by a horizontal divider.
     * Each line (header and options) is padded with 2 spaces on the left and right.
     *
     * @param header  the header text for the menu (e.g., "Main Menu").
     * @param options the list of option strings to display.
     */
    public static void printBoxedMenu(String header, String... options) {
        // Find the longest string among header and all option lines.
        int maxLength = header.length();
        for (String opt : options) {
            if (opt.length() > maxLength) {
                maxLength = opt.length();
            }
        }

        // Set horizontal padding to 2 spaces on each side.
        int horizontalPadding = 2;
        // Calculate the total box width based on the longest line and padding.
        int boxWidth = maxLength + horizontalPadding * 2;

        // Build the top border, divider line, and bottom border.
        String topBorder = "╔" + "═".repeat(boxWidth) + "╗";
        String dividerLine = "╠" + "═".repeat(boxWidth) + "╣";
        String bottomBorder = "╚" + "═".repeat(boxWidth) + "╝";

        // Use a StringBuilder to build the entire box.
        StringBuilder sb = new StringBuilder();
        sb.append(topBorder).append("\n");

        // Build the header line.
        int leftover = maxLength - header.length();
        String lineContent = " ".repeat(horizontalPadding)
                             + header
                             + " ".repeat(leftover)
                             + " ".repeat(horizontalPadding);
        sb.append("║").append(lineContent).append("║").append("\n");

        // Append the divider line between header and options.
        sb.append(dividerLine).append("\n");

        // Append each option line with the same padding.
        for (String opt : options) {
            int extraSpaces = maxLength - opt.length();
            String optionLine = " ".repeat(horizontalPadding)
                                + opt
                                + " ".repeat(extraSpaces)
                                + " ".repeat(horizontalPadding);
            sb.append("║").append(optionLine).append("║").append("\n");
        }

        // Append the bottom border.
        sb.append(bottomBorder);

        // Print the entire box.
        System.out.println(sb);
    }

}
