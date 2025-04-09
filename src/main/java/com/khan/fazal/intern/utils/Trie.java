package com.khan.fazal.intern.utils;

import java.util.*;

/**
 * A Trie (prefix tree) data structure used for efficient
 * prefix-based searching of contact names.
 */
public class Trie {

    /**
     * Represents each node in the Trie.
     * Contains a map of children and a list of contact names
     * that pass through this node.
     */
    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        List<String> contactNames = new ArrayList<>();
    }

    private final TrieNode root = new TrieNode();

    /**
     * Inserts a contact name into the Trie.
     * Maintains a list of contact names at each node for quick prefix lookup.
     *
     * @param name the contact name to insert
     */
    public void insert(String name) {
        TrieNode current = root;
        for (char ch : name.toLowerCase().toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
            current.contactNames.add(name);
        }
    }

    /**
     * Deletes a contact name from the Trie.
     * Removes the name from nodes along the path and prunes nodes if necessary.
     *
     * @param name the contact name to delete
     */
    public void delete(String name) {
        delete(root, name.toLowerCase(), 0);
    }

    /**
     * Helper method for deleting a name recursively.
     *
     * @param node the current Trie node
     * @param name the name to delete
     * @param index current character index in the name
     * @return true if the current branch can be pruned; false otherwise
     */
    private boolean delete(TrieNode node, String name, int index) {
        if (index == name.length()) return true;

        char ch = name.charAt(index);
        TrieNode child = node.children.get(ch);
        if (child == null) return false;

        child.contactNames.remove(name);

        if (delete(child, name, index + 1) && child.contactNames.isEmpty()) {
            node.children.remove(ch);
        }
        return node.children.isEmpty();
    }

    /**
     * Searches and returns all contact names starting with the given prefix.
     *
     * @param prefix the prefix to search for
     * @return list of contact names that match the prefix
     */
    public List<String> searchByPrefix(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toLowerCase().toCharArray()) {
            current = current.children.get(ch);
            if (current == null) return Collections.emptyList();
        }
        return current.contactNames;
    }
}
