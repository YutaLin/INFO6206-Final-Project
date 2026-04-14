package com.ecommerce.services;

import com.ecommerce.datastructures.PriorityQueue;
import com.ecommerce.models.Product;

import java.util.*;

/**
 * RecommendationService provides product recommendations
 * Uses: PriorityQueue to rank products by relevance/rating
 */
public class RecommendationService {

    /**
     * Get featured products using priority queue (highest rated products)
     * Time Complexity: O(n log k) where k is limit
     */
    public List<Product> getFeaturedProducts(List<Product> allProducts, int limit) {
        if (allProducts.isEmpty()) {
            return new ArrayList<>();
        }

        // Use max heap (reversed comparator) to get highest rated products
        PriorityQueue<Product> maxHeap = new PriorityQueue<>(
            (p1, p2) -> Double.compare(p2.getRating(), p1.getRating())
        );

        // Add all products to priority queue
        for (Product product : allProducts) {
            maxHeap.enqueue(product);
        }

        // Extract top products
        List<Product> featured = new ArrayList<>();
        int count = Math.min(limit, allProducts.size());

        for (int i = 0; i < count; i++) {
            if (!maxHeap.isEmpty()) {
                featured.add(maxHeap.dequeue());
            }
        }

        return featured;
    }

    /**
     * Get recommended products based on a given product
     * Recommends products from same category, sorted by rating
     * Time Complexity: O(n log k)
     */
    public List<Product> getRecommendedProducts(Product currentProduct, List<Product> allProducts, int limit) {
        if (currentProduct == null || allProducts.isEmpty()) {
            return new ArrayList<>();
        }

        // Filter products from same category, excluding current product
        List<Product> sameCategoryProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getCategory().equals(currentProduct.getCategory()) &&
                product.getId() != currentProduct.getId()) {
                sameCategoryProducts.add(product);
            }
        }

        // Use priority queue to get highest rated products
        return getFeaturedProducts(sameCategoryProducts, limit);
    }
}
