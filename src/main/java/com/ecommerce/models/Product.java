package com.ecommerce.models;

import java.util.Map;
import java.util.HashMap;

/**
 * Product model representing an electronic product in the store
 * Implements Comparable for sorting operations
 */
public class Product implements Comparable<Product> {
    private final int id;
    private final String name;
    private final String category;
    private final double price;
    private final double rating;
    private final String description;
    private final Map<String, String> specifications;
    private final String imageUrl;

    public Product(int id, String name, String category, double price, double rating,
                   String description, Map<String, String> specifications, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.specifications = specifications != null ? specifications : new HashMap<>();
        this.imageUrl = imageUrl;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getSpecifications() {
        return new HashMap<>(specifications);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Natural ordering by product ID for BST
     */
    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', price=$%.2f, rating=%.1f}",
                           id, name, price, rating);
    }

    /**
     * Get formatted price string
     */
    public String getFormattedPrice() {
        return String.format("$%.2f", price);
    }

    /**
     * Get specification value by key
     */
    public String getSpec(String key) {
        return specifications.getOrDefault(key, "N/A");
    }

    /**
     * Builder pattern for easier product creation
     */
    public static class Builder {
        private int id;
        private String name;
        private String category;
        private double price;
        private double rating;
        private String description;
        private Map<String, String> specifications = new HashMap<>();
        private String imageUrl = "";

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder rating(double rating) {
            this.rating = rating;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder addSpec(String key, String value) {
            this.specifications.put(key, value);
            return this;
        }

        public Builder specifications(Map<String, String> specifications) {
            this.specifications = specifications;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Product build() {
            return new Product(id, name, category, price, rating, description, specifications, imageUrl);
        }
    }
}
