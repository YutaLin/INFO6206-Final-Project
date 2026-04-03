package com.ecommerce.datastructures;

import com.ecommerce.models.Category;
import com.ecommerce.models.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Category Tree implementation using recursion for tree traversal
 * Demonstrates: Recursion for tree operations
 * Time Complexity: O(n) for traversal operations where n is number of categories
 */
public class CategoryTree {
    private Category root;

    public CategoryTree(Category root) {
        this.root = root;
    }

    /**
     * Recursively traverse all categories and apply action
     * Demonstrates: Recursion
     * Time Complexity: O(n)
     */
    public void traverseCategories(Category category, java.util.function.Consumer<Category> action) {
        if (category == null) {
            return;
        }

        // Process current category
        action.accept(category);

        // Recursively process subcategories
        for (Category subcategory : category.getSubcategories()) {
            traverseCategories(subcategory, action);
        }
    }

    /**
     * Recursively find a category by name
     * Demonstrates: Recursion with early return
     * Time Complexity: O(n) worst case
     */
    public Category findCategory(Category category, String name) {
        if (category == null) {
            return null;
        }

        // Base case: found the category
        if (category.getName().equalsIgnoreCase(name)) {
            return category;
        }

        // Recursive case: search in subcategories
        for (Category subcategory : category.getSubcategories()) {
            Category found = findCategory(subcategory, name);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    /**
     * Recursively get all products from a category and its subcategories
     * Demonstrates: Recursion with result accumulation
     * Time Complexity: O(n * m) where n is categories and m is products per category
     */
    public List<Product> getAllProducts(Category category) {
        List<Product> allProducts = new ArrayList<>();
        getAllProductsRecursive(category, allProducts);
        return allProducts;
    }

    private void getAllProductsRecursive(Category category, List<Product> accumulator) {
        if (category == null) {
            return;
        }

        // Add products from current category
        accumulator.addAll(category.getProducts());

        // Recursively add products from subcategories
        for (Category subcategory : category.getSubcategories()) {
            getAllProductsRecursive(subcategory, accumulator);
        }
    }

    /**
     * Recursively count total number of categories
     * Demonstrates: Recursion with numeric accumulation
     * Time Complexity: O(n)
     */
    public int countCategories(Category category) {
        if (category == null) {
            return 0;
        }

        int count = 1; // Count current category

        // Add counts from all subcategories
        for (Category subcategory : category.getSubcategories()) {
            count += countCategories(subcategory);
        }

        return count;
    }

    /**
     * Recursively get the depth of the category tree
     * Demonstrates: Recursion with max operation
     * Time Complexity: O(n)
     */
    public int getDepth(Category category) {
        if (category == null || category.getSubcategories().isEmpty()) {
            return 0;
        }

        int maxDepth = 0;
        for (Category subcategory : category.getSubcategories()) {
            maxDepth = Math.max(maxDepth, getDepth(subcategory));
        }

        return maxDepth + 1;
    }

    /**
     * Recursively print category tree structure
     * Demonstrates: Recursion with formatted output
     * Time Complexity: O(n)
     */
    public void printTree(Category category, int level) {
        if (category == null) {
            return;
        }

        // Print current category with indentation
        String indent = "  ".repeat(level);
        System.out.println(indent + category.getName() + " (" + category.getProducts().size() + " products)");

        // Recursively print subcategories
        for (Category subcategory : category.getSubcategories()) {
            printTree(subcategory, level + 1);
        }
    }

    /**
     * Get the root category
     */
    public Category getRoot() {
        return root;
    }

    /**
     * Convenience method to traverse from root
     */
    public void traverseAll(java.util.function.Consumer<Category> action) {
        traverseCategories(root, action);
    }

    /**
     * Convenience method to find category from root
     */
    public Category findCategory(String name) {
        return findCategory(root, name);
    }

    /**
     * Convenience method to get all products from root
     */
    public List<Product> getAllProducts() {
        return getAllProducts(root);
    }

    /**
     * Convenience method to count all categories
     */
    public int getTotalCategories() {
        return countCategories(root);
    }

    /**
     * Convenience method to get tree depth
     */
    public int getDepth() {
        return getDepth(root);
    }

    /**
     * Convenience method to print entire tree
     */
    public void printTree() {
        printTree(root, 0);
    }
}
