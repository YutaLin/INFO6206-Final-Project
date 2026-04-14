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
     * Convenience method to find category from root
     */
    public Category findCategory(String name) {
        return findCategory(root, name);
    }
}
