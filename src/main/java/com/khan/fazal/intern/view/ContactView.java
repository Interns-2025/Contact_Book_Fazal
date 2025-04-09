package com.khan.fazal.intern.view;

import com.khan.fazal.intern.model.Contact;
import com.khan.fazal.intern.service.ContactService;
import com.khan.fazal.intern.utils.PrintDash;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based view for the Contact Book application.
 * Provides a simple menu-driven interface for managing contacts.
 */
public class ContactView {
    private final ContactService service = new ContactService();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the main menu and handles user input in a loop.
     */
    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Contact Book ---");
            System.out.println("1. View Contacts");
            System.out.println("2. Add Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Search Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> displayContacts();
                case "2" -> addContact();
                case "3" -> deleteContact();
                case "4" -> searchContact();
                case "5" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Searches contacts by name prefix and displays matching results.
     * Results are sorted alphabetically and shown in tabular format.
     */
    private void searchContact() {
        System.out.print("Enter name prefix to search: ");
        String prefix = scanner.nextLine();
        List<Contact> results = service.searchContacts(prefix);

        if (results.isEmpty()) {
            System.out.println("No contacts found for prefix: " + prefix);
        } else {
            results.sort(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER));
            List<String[]> rows = new ArrayList<>();
            rows.add(new String[]{"Name", "Phone", "Email"});

            for (Contact c : results) {
                rows.add(new String[]{c.getName(), c.getPhone(), c.getEmail()});
            }

            PrintDash.printTable(rows);
        }
    }

    /**
     * Retrieves and displays all saved contacts in alphabetical order.
     * Uses the PrintDash utility to display a formatted table.
     */
    private void displayContacts() {
        List<Contact> contacts = service.getContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        contacts.sort(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER));

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Name", "Phone", "Email"});

        for (Contact c : contacts) {
            rows.add(new String[]{c.getName(), c.getPhone(), c.getEmail()});
        }

        PrintDash.printTable(rows);
    }

    /**
     * Prompts the user for new contact details and attempts to add the contact.
     * Validates phone and email before adding.
     */
    private void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (service.addContact(name, phone, email)) {
            System.out.println("Contact added.");
        } else {
            System.out.println("Invalid phone or email.");
        }
    }

    /**
     * Prompts the user for a contact name and deletes the contact if it exists.
     */
    private void deleteContact() {
        System.out.print("Enter name to delete: ");
        String name = scanner.nextLine();
        service.deleteContact(name);
        System.out.println("Contact deleted if existed.");
    }
}