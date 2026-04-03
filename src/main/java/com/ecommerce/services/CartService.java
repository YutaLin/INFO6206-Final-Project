package com.ecommerce.services;

import com.ecommerce.datastructures.LinkedList;
import com.ecommerce.datastructures.HashTable;
import com.ecommerce.models.CartItem;
import com.ecommerce.models.Product;

import java.util.List;

/**
 * CartService manages shopping cart using custom data structures
 * Uses: LinkedList for cart items, HashTable for quick product lookup
 */
public class CartService {
    private final LinkedList<CartItem> cartItems;
    private final HashTable<Integer, CartItem> productIdToCartItem;

    public CartService() {
        this.cartItems = new LinkedList<>();
        this.productIdToCartItem = new HashTable<>();
    }

    /**
     * Add item to cart
     * Time Complexity: O(1) for hash lookup + O(1) for list add
     */
    public void addItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        CartItem existingItem = productIdToCartItem.get(product.getId());

        if (existingItem != null) {
            // Product already in cart, update quantity
            existingItem.incrementQuantity(quantity);
        } else {
            // New product, add to cart
            CartItem newItem = new CartItem(product, quantity);
            cartItems.add(newItem);
            productIdToCartItem.put(product.getId(), newItem);
        }
    }

    /**
     * Add single item to cart
     */
    public void addItem(Product product) {
        addItem(product, 1);
    }

    /**
     * Remove item from cart by product ID
     * Time Complexity: O(1) for hash lookup + O(n) for list removal
     */
    public boolean removeItem(int productId) {
        CartItem item = productIdToCartItem.remove(productId);
        if (item != null) {
            cartItems.remove(item);
            return true;
        }
        return false;
    }

    /**
     * Remove item from cart by product
     */
    public boolean removeItem(Product product) {
        return removeItem(product.getId());
    }

    /**
     * Update quantity of an item
     * Time Complexity: O(1)
     */
    public boolean updateQuantity(int productId, int newQuantity) {
        if (newQuantity <= 0) {
            return removeItem(productId);
        }

        CartItem item = productIdToCartItem.get(productId);
        if (item != null) {
            item.setQuantity(newQuantity);
            return true;
        }
        return false;
    }

    /**
     * Get all cart items
     * Time Complexity: O(n)
     */
    public List<CartItem> getItems() {
        return cartItems.toList();
    }

    /**
     * Get cart item by product ID
     * Time Complexity: O(1)
     */
    public CartItem getItem(int productId) {
        return productIdToCartItem.get(productId);
    }

    /**
     * Check if product is in cart
     * Time Complexity: O(1)
     */
    public boolean containsProduct(int productId) {
        return productIdToCartItem.containsKey(productId);
    }

    /**
     * Get total number of items (sum of quantities)
     * Time Complexity: O(n)
     */
    public int getTotalItemCount() {
        int count = 0;
        for (CartItem item : cartItems.toList()) {
            count += item.getQuantity();
        }
        return count;
    }

    /**
     * Get number of unique products in cart
     * Time Complexity: O(1)
     */
    public int getUniqueItemCount() {
        return cartItems.size();
    }

    /**
     * Get total price of all items in cart
     * Time Complexity: O(n)
     */
    public double getTotal() {
        double total = 0.0;
        for (CartItem item : cartItems.toList()) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Get formatted total price
     */
    public String getFormattedTotal() {
        return String.format("$%.2f", getTotal());
    }

    /**
     * Clear all items from cart
     * Time Complexity: O(1)
     */
    public void clear() {
        cartItems.clear();
        productIdToCartItem.clear();
    }

    /**
     * Check if cart is empty
     * Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    /**
     * Get cart summary
     */
    public String getSummary() {
        return String.format("Cart: %d items, Total: %s",
                           getTotalItemCount(), getFormattedTotal());
    }
}
