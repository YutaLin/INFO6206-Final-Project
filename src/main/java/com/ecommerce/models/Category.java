package com.ecommerce.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Category model representing a product category in a tree structure
 * Supports hierarchical organization (e.g., Electronics -> Laptops -> Gaming Laptops)
 */
public class Category {
    private final String name;
    private final Category parent;
    private final List<Category> subcategories;
    private final List<Product> products;

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
        this.subcategories = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public Category(String name) {
        this(name, null);
    }

    // Getters
    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public List<Category> getSubcategories() {
        return new ArrayList<>(subcategories);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Add a subcategory
     */
    public void addSubcategory(Category subcategory) {
        if (!subcategories.contains(subcategory)) {
            subcategories.add(subcategory);
        }
    }

    /**
     * Add a product to this category
     */
    public void addProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
        }
    }

    /**
     * Remove a product from this category
     */
    public void removeProduct(Product product) {
        products.remove(product);
    }

    /**
     * Get full category path (e.g., "Electronics > Laptops > Gaming Laptops")
     */
    public String getFullPath() {
        if (parent == null) {
            return name;
        }
        return parent.getFullPath() + " > " + name;
    }

    /**
     * Check if this category has subcategories
     */
    public boolean hasSubcategories() {
        return !subcategories.isEmpty();
    }

    /**
     * Check if this category has products
     */
    public boolean hasProducts() {
        return !products.isEmpty();
    }

    /**
     * Get total number of products including subcategories
     */
    public int getTotalProductCount() {
        int count = products.size();
        for (Category subcategory : subcategories) {
            count += subcategory.getTotalProductCount();
        }
        return count;
    }

    /**
     * Check if this is a leaf category (no subcategories)
     */
    public boolean isLeaf() {
        return subcategories.isEmpty();
    }

    /**
     * Check if this is a root category (no parent)
     */
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public String toString() {
        return name + " (" + products.size() + " products, " + subcategories.size() + " subcategories)";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category category = (Category) obj;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
