package com.khan.fazal.intern.controller;

import com.khan.fazal.intern.view.ContactGUI;
import com.khan.fazal.intern.view.ContactView;

import java.util.Scanner;

/**
 * Handles the initial interaction with the user and launches
 * the appropriate interface (CLI or GUI) based on user input.
 *
 * This class serves as the main controller responsible for
 * deciding the interaction mode and initiating the respective
 * view components accordingly.
 */
public class ContactController {

    /**
     * Displays a welcome message and prompts the user to choose
     * between CLI and GUI. Based on the choice, the corresponding
     * interface is launched. Invalid input exits the program.
     */
    public void launchApp() {
        System.out.println("\n\t\tWelcome to Contact Manager!\n");
        System.out.println("Would you like to continue with CLI or GUI:\n");
        System.out.println("1. CLI (Console)");
        System.out.println("2. GUI (Swing)");
        System.out.print("Enter choice (1 or 2): ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> new ContactView().displayMenu();
            case "2" -> javax.swing.SwingUtilities.invokeLater(ContactGUI::new);
            default -> System.out.println("Invalid choice. Exiting...");
        }
    }
}
