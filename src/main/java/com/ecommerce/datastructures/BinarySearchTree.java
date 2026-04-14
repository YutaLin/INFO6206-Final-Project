package com.ecommerce.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Custom Binary Search Tree implementation
 * Time Complexity: O(log n) average case for insert, search, delete
 * Space Complexity: O(n)
 */
public class BinarySearchTree<T extends Comparable<T>> {
    private class Node {
        T data;
        Node left;
        Node right;

        Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Insert a value into the BST
     * Time Complexity: O(log n) average, O(n) worst case
     */
    public void insert(T data) {
        root = insertRec(root, data);
        size++;
    }

    private Node insertRec(Node node, T data) {
        if (node == null) {
            return new Node(data);
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = insertRec(node.left, data);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, data);
        }
        // If equal, don't insert duplicate

        return node;
    }

    /**
     * Delete a value from the BST
     * Time Complexity: O(log n) average, O(n) worst case
     */
    public boolean delete(T data) {
        int initialSize = size;
        root = deleteRec(root, data);
        return size < initialSize;
    }

    private Node deleteRec(Node node, T data) {
        if (node == null) {
            return null;
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.left = deleteRec(node.left, data);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, data);
        } else {
            // Found node to delete
            size--;

            // Case 1: No children
            if (node.left == null && node.right == null) {
                return null;
            }
            // Case 2: One child
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // Case 3: Two children
            // Find inorder successor (smallest in right subtree)
            Node successor = findMin(node.right);
            node.data = successor.data;
            node.right = deleteRec(node.right, successor.data);
            size++; // Compensate for the decrement that will happen in recursive call
        }

        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Inorder traversal of the BST
     * Time Complexity: O(n)
     */
    public void inorderTraversal(Consumer<T> action) {
        inorderTraversalRec(root, action);
    }

    private void inorderTraversalRec(Node node, Consumer<T> action) {
        if (node != null) {
            inorderTraversalRec(node.left, action);
            action.accept(node.data);
            inorderTraversalRec(node.right, action);
        }
    }

    /**
     * Get all elements in sorted order
     * Time Complexity: O(n)
     */
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        inorderTraversal(list::add);
        return list;
    }
}
