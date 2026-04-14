package com.ecommerce.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom Hash Table implementation using chaining for collision resolution
 * Time Complexity: O(1) average case for put, get, remove
 * Space Complexity: O(n)
 */
public class HashTable<K, V> {
    private class Entry {
        K key;
        V value;
        Entry next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Object[] table;
    private int size;
    private int capacity;

    public HashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.table = new Object[capacity];
        this.size = 0;
    }

    public HashTable(int capacity) {
        this.capacity = capacity;
        this.table = new Object[capacity];
        this.size = 0;
    }

    /**
     * Hash function to compute index
     */
    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    /**
     * Put a key-value pair into the hash table
     * Time Complexity: O(1) average case
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        // Check if we need to resize
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int index = hash(key);
        Entry current = (Entry) table[index];

        // Check if key already exists
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        // Add new entry at the beginning of the chain
        Entry newEntry = new Entry(key, value);
        newEntry.next = (Entry) table[index];
        table[index] = newEntry;
        size++;
    }

    /**
     * Get value by key
     * Time Complexity: O(1) average case
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }

        int index = hash(key);
        Entry current = (Entry) table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Remove a key-value pair
     * Time Complexity: O(1) average case
     */
    public V remove(K key) {
        if (key == null) {
            return null;
        }

        int index = hash(key);
        Entry current = (Entry) table[index];
        Entry prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }

        return null;
    }

    /**
     * Check if key exists
     * Time Complexity: O(1) average case
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Resize the hash table when load factor exceeds threshold
     */
    private void resize() {
        int newCapacity = capacity * 2;
        Object[] oldTable = table;
        table = new Object[newCapacity];
        capacity = newCapacity;
        size = 0;

        // Rehash all entries
        for (Object obj : oldTable) {
            Entry entry = (Entry) obj;
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    public int size() {
        return size;
    }

    public void clear() {
        table = new Object[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
        size = 0;
    }
}
