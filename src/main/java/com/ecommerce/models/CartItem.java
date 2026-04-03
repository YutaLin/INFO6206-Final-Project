package com.ecommerce.models;

/**
 * CartItem model representing a product in the shopping cart with quantity
 */
public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(Product product) {
        this(product, 1);
    }

    // Getters
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Set quantity
     */
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
    }

    /**
     * Increment quantity by 1
     */
    public void incrementQuantity() {
        this.quantity++;
    }

    /**
     * Increment quantity by amount
     */
    public void incrementQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.quantity += amount;
    }

    /**
     * Decrement quantity by 1
     */
    public void decrementQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }

    /**
     * Get subtotal (price * quantity)
     */
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    /**
     * Get formatted subtotal
     */
    public String getFormattedSubtotal() {
        return String.format("$%.2f", getSubtotal());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CartItem cartItem = (CartItem) obj;
        return product.equals(cartItem.product);
    }

    @Override
    public int hashCode() {
        return product.hashCode();
    }

    @Override
    public String toString() {
        return String.format("CartItem{product=%s, quantity=%d, subtotal=$%.2f}",
                           product.getName(), quantity, getSubtotal());
    }
}
