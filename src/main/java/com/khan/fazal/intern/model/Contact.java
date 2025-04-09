package com.khan.fazal.intern.model;

/**
 * Represents a single contact with basic information:
 * name, phone number, and email address.
 *
 * This class is used as the data model for storing and
 * managing contact entries.
 */
public class Contact {
    private String name;
    private String phone;
    private String email;

    /**
     * Constructs a contact with the specified name, phone, and email.
     *
     * @param name  the full name of the contact
     * @param phone the phone number of the contact
     * @param email the email address of the contact
     */
    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Returns the contact's name.
     *
     * @return name of the contact
     */
    public String getName() { return name; }

    /**
     * Returns the contact's phone number.
     *
     * @return phone number of the contact
     */
    public String getPhone() { return phone; }

    /**
     * Returns the contact's email address.
     *
     * @return email of the contact
     */
    public String getEmail() { return email; }

    /**
     * Converts the contact to a CSV-friendly string format.
     *
     * @return comma-separated values of name, phone, and email
     */
    @Override
    public String toString() {
        return name + "," + phone + "," + email;
    }
}
