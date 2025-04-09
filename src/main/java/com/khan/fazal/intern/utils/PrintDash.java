package com.khan.fazal.intern.utils;

import java.util.List;

/**
 * Utility class to print data in a formatted table.
 */
public class PrintDash {

    /**
     * Prints the given rows as a formatted table.
     *
     * @param rows List of rows, where each row is a String array
     */
    public static void printTable(List<String[]> rows) {
        int columnCount = rows.get(0).length;
        int[] maxWidths = new int[columnCount];

        for (String[] row : rows) {
            for (int i = 0; i < columnCount; i++) {
                if (i < row.length) {
                    maxWidths[i] = Math.max(maxWidths[i], row[i].trim().length());
                }
            }
        }

        printSeparator(maxWidths);
        printRow(rows.get(0), maxWidths); // Header
        printSeparator(maxWidths);

        for (int i = 1; i < rows.size(); i++) {
            printRow(rows.get(i), maxWidths);
        }

        printSeparator(maxWidths);
    }

    /**
     * Prints a single row with padding.
     *
     * @param columns Array of column values
     * @param widths  Max widths for each column
     */
    private static void printRow(String[] columns, int[] widths) {
        System.out.print("|");
        for (int i = 0; i < widths.length; i++) {
            String cell = i < columns.length ? columns[i].trim() : "";
            System.out.printf(" %-" + widths[i] + "s |", cell);
        }
        System.out.println();
    }

    /**
     * Prints a separator line based on column widths.
     *
     * @param widths Max widths for each column
     */
    private static void printSeparator(int[] widths) {
        System.out.print("+");
        for (int width : widths) {
            System.out.print("-".repeat(width + 2) + "+");
        }
        System.out.println();
    }
}