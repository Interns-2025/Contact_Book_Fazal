package com.khan.fazal.intern.service;

import com.khan.fazal.intern.model.Contact;
import com.khan.fazal.intern.dao.ContactRepository;
import com.khan.fazal.intern.utils.InputValidator;
import com.khan.fazal.intern.utils.Trie;
import java.util.*;

/**
 * Provides services for managing contacts including adding,
 * deleting, listing, and searching contacts using a Trie
 * for efficient prefix-based search.
 */
public class ContactService {
    private final ContactRepository repository;
    private final Trie trie;

    /**
     * Initializes the contact service by loading all existing
     * contacts from the repository and populating the Trie.
     */
    public ContactService() {
        this.repository = new ContactRepository();
        this.trie = new Trie();
        repository.getAllContacts().forEach(c -> trie.insert(c.getName()));
    }

    /**
     * Retrieves all saved contacts.
     *
     * @return a list of all contacts
     */
    public List<Contact> getContacts() {
        return repository.getAllContacts();
    }

    /**
     * Adds a new contact after validating phone and email.
     * Inserts the contact into both the repository and the Trie.
     *
     * @param name  the contact's name
     * @param phone the contact's phone number
     * @param email the contact's email address
     * @return true if the contact was added successfully; false otherwise
     */
    public boolean addContact(String name, String phone, String email) {
        if (!InputValidator.isValidPhone(phone) || !InputValidator.isValidEmail(email)) {
            return false;
        }
        Contact contact = new Contact(toTitleCase(name), phone, email);
        repository.addContact(contact);
        trie.insert(name);
        return true;
    }

    /**
     * Deletes a contact from the repository and the Trie using the given name.
     *
     * @param name the name of the contact to be deleted
     */
    public void deleteContact(String name) {
        repository.deleteContact(name);
        trie.delete(name);
    }

    /**
     * Searches for contacts that match the given prefix in name, phone, or email.
     *
     * @param prefix the search keyword
     * @return a list of matching contacts
     */
    public List<Contact> searchContacts(String prefix) {
        String lowerPrefix = prefix.toLowerCase();
        Set<String> nameMatches = new HashSet<>(trie.searchByPrefix(lowerPrefix));
        List<Contact> matches = new ArrayList<>();

        for (Contact c : getContacts()) {
            if (nameMatches.contains(c.getName().toLowerCase()) ||
                    c.getPhone().contains(lowerPrefix) ||
                    c.getEmail().toLowerCase().contains(lowerPrefix)) {
                matches.add(c);
            }
        }

        return matches;
    }

    /**
     * Converts a string to title case (e.g., "john doe" → "John Doe").
     *
     * @param input the input string
     * @return the title-cased string
     */
    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) return input;

        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }
            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
