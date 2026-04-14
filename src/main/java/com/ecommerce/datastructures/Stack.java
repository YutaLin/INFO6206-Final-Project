package com.ecommerce.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom Stack implementation using linked nodes
 * Time Complexity: O(1) for push, pop, peek
 * Space Complexity: O(n)
 * Used for: Browsing history (back/forward navigation)
 */
public class Stack<T> {
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Push an element onto the stack
     * Time Complexity: O(1)
     */
    public void push(T data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Pop an element from the stack
     * Time Complexity: O(1)
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /**
     * Peek at the top element without removing it
     * Time Complexity: O(1)
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.data;
    }

    /**
     * Check if stack is empty
     * Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Get size of stack
     * Time Complexity: O(1)
     */
    public int size() {
        return size;
    }

    /**
     * Clear all elements from stack
     * Time Complexity: O(1)
     */
    public void clear() {
        top = null;
        size = 0;
    }

    /**
     * Convert stack to list (top to bottom)
     * Time Complexity: O(n)
     */
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node current = top;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }
}
