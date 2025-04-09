package com.khan.fazal.intern.app;

import com.khan.fazal.intern.controller.ContactController;

/**
 * Entry point of the Contact Manager application.
 *
 * This class initializes and starts the application by
 * delegating control to the ContactController, which handles
 * user interaction through CLI or GUI based on user input.
 */
public class Main {
    public static void main(String[] args) {
        new ContactController().launchApp();
    }
}
