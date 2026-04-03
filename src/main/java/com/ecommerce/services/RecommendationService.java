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

    /**
     * Get top products by price-to-rating ratio (best value)
     * Time Complexity: O(n log k)
     */
    public List<Product> getBestValueProducts(List<Product> allProducts, int limit) {
        if (allProducts.isEmpty()) {
            return new ArrayList<>();
        }

        // Calculate value score: rating / (price / 100)
        // Higher rating and lower price = better value
        PriorityQueue<ProductWithScore> valueHeap = new PriorityQueue<>(
            (p1, p2) -> Double.compare(p2.score, p1.score)
        );

        for (Product product : allProducts) {
            double valueScore = product.getRating() / (product.getPrice() / 100.0);
            valueHeap.enqueue(new ProductWithScore(product, valueScore));
        }

        List<Product> bestValue = new ArrayList<>();
        int count = Math.min(limit, allProducts.size());

        for (int i = 0; i < count; i++) {
            if (!valueHeap.isEmpty()) {
                bestValue.add(valueHeap.dequeue().product);
            }
        }

        return bestValue;
    }

    /**
     * Get products sorted by popularity (rating * arbitrary popularity factor)
     * Time Complexity: O(n log k)
     */
    public List<Product> getPopularProducts(List<Product> allProducts, int limit) {
        if (allProducts.isEmpty()) {
            return new ArrayList<>();
        }

        // Simulate popularity score based on rating and price range
        PriorityQueue<ProductWithScore> popularityHeap = new PriorityQueue<>(
            (p1, p2) -> Double.compare(p2.score, p1.score)
        );

        for (Product product : allProducts) {
            // Higher rating products in mid-price range are considered more popular
            double popularityScore = product.getRating() * (1 + Math.min(product.getPrice() / 1000.0, 1.0));
            popularityHeap.enqueue(new ProductWithScore(product, popularityScore));
        }

        List<Product> popular = new ArrayList<>();
        int count = Math.min(limit, allProducts.size());

        for (int i = 0; i < count; i++) {
            if (!popularityHeap.isEmpty()) {
                popular.add(popularityHeap.dequeue().product);
            }
        }

        return popular;
    }

    /**
     * Get new arrivals (highest product IDs)
     * Time Complexity: O(n log k)
     */
    public List<Product> getNewArrivals(List<Product> allProducts, int limit) {
        if (allProducts.isEmpty()) {
            return new ArrayList<>();
        }

        // Use max heap based on product ID (assuming higher ID = newer)
        PriorityQueue<Product> newProductsHeap = new PriorityQueue<>(
            (p1, p2) -> Integer.compare(p2.getId(), p1.getId())
        );

        for (Product product : allProducts) {
            newProductsHeap.enqueue(product);
        }

        List<Product> newArrivals = new ArrayList<>();
        int count = Math.min(limit, allProducts.size());

        for (int i = 0; i < count; i++) {
            if (!newProductsHeap.isEmpty()) {
                newArrivals.add(newProductsHeap.dequeue());
            }
        }

        return newArrivals;
    }

    /**
     * Helper class to store product with calculated score
     */
    private static class ProductWithScore {
        Product product;
        double score;

        ProductWithScore(Product product, double score) {
            this.product = product;
            this.score = score;
        }
    }

    /**
     * Get performance information about recommendation algorithms
     */
    public String getPerformanceInfo() {
        return "Recommendation Service uses Priority Queue (Binary Heap)\n" +
               "- Time Complexity: O(n log k) for top-k recommendations\n" +
               "- Space Complexity: O(n) for storing all products\n" +
               "- Enqueue/Dequeue: O(log n) per operation";
    }
}
