package com.ecommerce.datastructures;

/**
 * Custom Doubly Linked List implementation
 * Time Complexity: O(1) for add/remove at ends, O(n) for access by index
 * Space Complexity: O(n)
 * Used for: Shopping cart items
 */
public class LinkedList<T> {
    private class Node {
        T data;
        Node next;
        Node prev;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Add element at the end of the list
     * Time Complexity: O(1)
     */
    public void add(T data) {
        Node newNode = new Node(data);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
    }

    /**
     * Add element at the beginning of the list
     * Time Complexity: O(1)
     */
    public void addFirst(T data) {
        Node newNode = new Node(data);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }

        size++;
    }

    /**
     * Add element at specific index
     * Time Complexity: O(n)
     */
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            addFirst(data);
            return;
        }

        if (index == size) {
            add(data);
            return;
        }

        Node newNode = new Node(data);
        Node current = getNodeAt(index);

        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;

        size++;
    }

    /**
     * Get element at specific index
     * Time Complexity: O(n)
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        return getNodeAt(index).data;
    }

    /**
     * Helper method to get node at specific index
     */
    private Node getNodeAt(int index) {
        Node current;

        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current;
    }

    /**
     * Remove element at specific index
     * Time Complexity: O(n)
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node nodeToRemove = getNodeAt(index);
        return removeNode(nodeToRemove);
    }

    /**
     * Remove first occurrence of element
     * Time Complexity: O(n)
     */
    public boolean remove(T data) {
        Node current = head;

        while (current != null) {
            if (current.data.equals(data)) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }

        return false;
    }

    /**
     * Helper method to remove a node
     */
    private T removeNode(Node node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        size--;
        return node.data;
    }

    /**
     * Find index of first occurrence of element
     * Time Complexity: O(n)
     */
    public int indexOf(T data) {
        Node current = head;
        int index = 0;

        while (current != null) {
            if (current.data.equals(data)) {
                return index;
            }
            current = current.next;
            index++;
        }

        return -1;
    }

    /**
     * Check if list contains element
     * Time Complexity: O(n)
     */
    public boolean contains(T data) {
        return indexOf(data) != -1;
    }

    /**
     * Convert to Java List for iteration
     * Time Complexity: O(n)
     */
    public java.util.List<T> toList() {
        java.util.List<T> list = new java.util.ArrayList<>();
        Node current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
