package com.khan.fazal.intern.dao;

import com.khan.fazal.intern.model.Contact;
import java.io.*;
import java.util.*;

/**
 * Handles data persistence and retrieval for contact records.
 *
 * This repository provides mechanisms to load, save, add, and delete
 * contacts stored in a CSV file located in the resources folder.
 */
public class ContactRepository {
    private final String filePath = "src/main/resources/contacts.csv";
    private final List<Contact> contacts = new ArrayList<>();

    /**
     * Constructor initializes the repository by loading contacts
     * from the CSV file, if available.
     */
    public ContactRepository() {
        loadContacts();
    }

    /**
     * Loads all contact records from the CSV file into memory.
     * Each line is split into name, phone, and email. Only valid
     * records with all three fields are considered.
     */
    private void loadContacts() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    contacts.add(new Contact(data[0], data[1], data[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("No saved contacts found.");
        }
    }

    /**
     * Returns the list of all contacts currently loaded in memory.
     *
     * @return list of contact objects
     */
    public List<Contact> getAllContacts() {
        return contacts;
    }

    /**
     * Adds a new contact to the in-memory list and persists it to the file.
     *
     * @param contact the contact to be added
     */
    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts();
    }

    /**
     * Deletes a contact from the list by matching the name (case-insensitive),
     * then updates the CSV file to reflect the change.
     *
     * @param name the name of the contact to delete
     */
    public void deleteContact(String name) {
        contacts.removeIf(c -> c.getName().equalsIgnoreCase(name));
        saveContacts();
    }

    /**
     * Saves all contacts from memory to the CSV file, one per line.
     */
    private void saveContacts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Contact c : contacts) {
                bw.write(c.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts.");
        }
    }
}
