package com.khan.fazal.intern.view;

import com.khan.fazal.intern.model.Contact;
import com.khan.fazal.intern.service.ContactService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * GUI view for the Contact Manager application.
 * Allows users to view, search, add, and delete contacts through a Swing interface.
 */
public class ContactGUI extends JFrame {
    private final ContactService service = new ContactService();
    private final DefaultTableModel tableModel;
    private final JTable contactTable;
    private final JTextField searchField;

    // Debounce variables
    private Timer debounceTimer = new Timer();
    private static final long DEBOUNCE_DELAY = 1000; // 1 second delay

    /**
     * Initializes the GUI components, sets up layout,
     * attaches event listeners, and loads initial data.
     */
    public ContactGUI() {
        setTitle("Contact Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLayout(new BorderLayout());

        // Top panel: Search bar
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        topPanel.add(new JLabel("🔍 Search: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Center panel: Contact table
        tableModel = new DefaultTableModel(new String[]{"Name", "Phone", "Email"}, 0);
        contactTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(contactTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel: Action buttons
        JPanel bottomPanel = new JPanel();
        JButton addBtn = new JButton("➕ Add Contact");
        JButton deleteBtn = new JButton("🗑 Delete Selected");
        JButton refreshBtn = new JButton("🔄 Refresh");

        bottomPanel.add(addBtn);
        bottomPanel.add(deleteBtn);
        bottomPanel.add(refreshBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Search field listener with debounce
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                debouncedSearchAndUpdate();
            }

            public void removeUpdate(DocumentEvent e) {
                debouncedSearchAndUpdate();
            }

            public void changedUpdate(DocumentEvent e) {
                debouncedSearchAndUpdate();
            }
        });

        // Add button action: Show dialog and insert contact
        addBtn.addActionListener(e -> showAddDialog());

        // Delete button action: Remove selected contact
        deleteBtn.addActionListener(e -> {
            int selectedRow = contactTable.getSelectedRow();
            if (selectedRow != -1) {
                String name = (String) tableModel.getValueAt(selectedRow, 0);
                service.deleteContact(name);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a contact to delete.");
            }
        });

        // Refresh button action: Reload all contacts
        refreshBtn.addActionListener(e -> refreshTable());

        refreshTable();
        setVisible(true);
    }

    /**
     * Debounced search method triggered on typing in search field.
     * Delays search until typing pauses for a certain duration.
     */
    private void debouncedSearchAndUpdate() {
        debounceTimer.cancel(); // Cancel existing scheduled task
        debounceTimer = new Timer(); // Create a new timer instance

        debounceTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Ensure UI updates happen on the Event Dispatch Thread
                SwingUtilities.invokeLater(() -> searchAndUpdate());
            }
        }, DEBOUNCE_DELAY);
    }

    /**
     * Performs a search based on user input and updates the table with results.
     */
    private void searchAndUpdate() {
        String query = searchField.getText();
        if (query.isEmpty()) {
            refreshTable();
        } else {
            List<Contact> results = service.searchContacts(query);
            updateTable(results);
        }
    }

    /**
     * Displays a dialog box to input new contact details.
     * Validates input and adds the contact if valid.
     */
    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();

        Object[] fields = {
                "Name:", nameField,
                "Phone:", phoneField,
                "Email:", emailField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Contact",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (service.addContact(name, phone, email)) {
                JOptionPane.showMessageDialog(this, "Contact added.");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid phone or email.");
            }
        }
    }

    /**
     * Refreshes the table with the full contact list from the service.
     */
    private void refreshTable() {
        updateTable(service.getContacts());
    }

    /**
     * Updates the contact table with a given list of contacts.
     * Sorts the list alphabetically before displaying.
     *
     * @param contacts list of contacts to show in the table
     */
    private void updateTable(List<Contact> contacts) {
        tableModel.setRowCount(0);
        contacts.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        for (Contact c : contacts) {
            tableModel.addRow(new String[]{c.getName(), c.getPhone(), c.getEmail()});
        }
    }
}
