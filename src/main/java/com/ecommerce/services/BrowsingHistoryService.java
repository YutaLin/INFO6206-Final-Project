package com.ecommerce.services;

import com.ecommerce.datastructures.Stack;
import com.ecommerce.models.Product;

import java.util.List;

/**
 * BrowsingHistoryService manages browsing history using stack
 * Uses: Stack for back/forward navigation
 * Demonstrates: Stack operations for browser-like navigation
 */
public class BrowsingHistoryService {
    private final Stack<Product> backStack;
    private final Stack<Product> forwardStack;
    private Product currentProduct;

    public BrowsingHistoryService() {
        this.backStack = new Stack<>();
        this.forwardStack = new Stack<>();
        this.currentProduct = null;
    }

    /**
     * Visit a new product (like clicking on a product)
     * Time Complexity: O(1)
     */
    public void visit(Product product) {
        if (product == null) {
            return;
        }

        // If there's a current product, push it to back stack
        if (currentProduct != null) {
            backStack.push(currentProduct);
        }

        // Clear forward stack when visiting a new product
        forwardStack.clear();

        // Set new current product
        currentProduct = product;
    }

    /**
     * Go back to previous product
     * Time Complexity: O(1)
     */
    public Product back() {
        if (!canGoBack()) {
            return currentProduct;
        }

        // Push current to forward stack
        if (currentProduct != null) {
            forwardStack.push(currentProduct);
        }

        // Pop from back stack
        currentProduct = backStack.pop();
        return currentProduct;
    }

    /**
     * Go forward to next product
     * Time Complexity: O(1)
     */
    public Product forward() {
        if (!canGoForward()) {
            return currentProduct;
        }

        // Push current to back stack
        if (currentProduct != null) {
            backStack.push(currentProduct);
        }

        // Pop from forward stack
        currentProduct = forwardStack.pop();
        return currentProduct;
    }

    /**
     * Check if can go back
     * Time Complexity: O(1)
     */
    public boolean canGoBack() {
        return !backStack.isEmpty();
    }

    /**
     * Check if can go forward
     * Time Complexity: O(1)
     */
    public boolean canGoForward() {
        return !forwardStack.isEmpty();
    }

    /**
     * Get size of back history
     * Time Complexity: O(1)
     */
    public int getBackHistorySize() {
        return backStack.size();
    }
}
