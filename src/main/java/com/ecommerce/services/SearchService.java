package com.ecommerce.services;

import com.ecommerce.models.Product;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SearchService provides search and sort functionality
 * Demonstrates: Multiple sorting algorithms, binary search, linear search
 */
public class SearchService {

    /**
     * Linear search by product name
     * Time Complexity: O(n)
     */
    public List<Product> searchByName(List<Product> products, String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(products);
        }

        String lowerQuery = query.toLowerCase().trim();
        List<Product> results = new ArrayList<>();

        // Linear search through all products
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(lowerQuery)) {
                results.add(product);
            }
        }

        return results;
    }

    /**
     * Search by specifications
     * Time Complexity: O(n * m) where m is average specs per product
     */
    public List<Product> searchBySpecs(List<Product> products, String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>(products);
        }

        String lowerQuery = query.toLowerCase().trim();
        List<Product> results = new ArrayList<>();

        for (Product product : products) {
            // Search in description
            if (product.getDescription().toLowerCase().contains(lowerQuery)) {
                results.add(product);
                continue;
            }

            // Search in specifications
            for (Map.Entry<String, String> spec : product.getSpecifications().entrySet()) {
                String specValue = (spec.getKey() + " " + spec.getValue()).toLowerCase();
                if (specValue.contains(lowerQuery)) {
                    results.add(product);
                    break;
                }
            }
        }

        return results;
    }

    /**
     * Binary search for product by ID (requires sorted list)
     * Time Complexity: O(log n)
     */
    public Product binarySearchById(List<Product> sortedProducts, int targetId) {
        int left = 0;
        int right = sortedProducts.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Product midProduct = sortedProducts.get(mid);

            if (midProduct.getId() == targetId) {
                return midProduct;
            } else if (midProduct.getId() < targetId) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    /**
     * Filter products by price range
     * Time Complexity: O(n)
     */
    public List<Product> filterByPriceRange(List<Product> products, double minPrice, double maxPrice) {
        List<Product> results = new ArrayList<>();

        for (Product product : products) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                results.add(product);
            }
        }

        return results;
    }

    /**
     * Filter products by minimum rating
     * Time Complexity: O(n)
     */
    public List<Product> filterByRating(List<Product> products, double minRating) {
        List<Product> results = new ArrayList<>();

        for (Product product : products) {
            if (product.getRating() >= minRating) {
                results.add(product);
            }
        }

        return results;
    }

    /**
     * Sort products by price using Quick Sort
     * Time Complexity: O(n log n) average case
     */
    public void sortByPrice(List<Product> products, boolean ascending) {
        quickSort(products, 0, products.size() - 1,
                 (p1, p2) -> ascending ?
                     Double.compare(p1.getPrice(), p2.getPrice()) :
                     Double.compare(p2.getPrice(), p1.getPrice()));
    }

    /**
     * Sort products by rating using Quick Sort
     * Time Complexity: O(n log n) average case
     */
    public void sortByRating(List<Product> products, boolean ascending) {
        quickSort(products, 0, products.size() - 1,
                 (p1, p2) -> ascending ?
                     Double.compare(p1.getRating(), p2.getRating()) :
                     Double.compare(p2.getRating(), p1.getRating()));
    }

    /**
     * Sort products by name using Merge Sort
     * Time Complexity: O(n log n)
     */
    public void sortByName(List<Product> products, boolean ascending) {
        List<Product> sorted = mergeSort(products,
                (p1, p2) -> ascending ?
                    p1.getName().compareToIgnoreCase(p2.getName()) :
                    p2.getName().compareToIgnoreCase(p1.getName()));

        products.clear();
        products.addAll(sorted);
    }

    /**
     * QuickSort implementation
     * Time Complexity: O(n log n) average, O(n²) worst case
     */
    private void quickSort(List<Product> products, int low, int high, Comparator<Product> comparator) {
        if (low < high) {
            int partitionIndex = partition(products, low, high, comparator);
            quickSort(products, low, partitionIndex - 1, comparator);
            quickSort(products, partitionIndex + 1, high, comparator);
        }
    }

    private int partition(List<Product> products, int low, int high, Comparator<Product> comparator) {
        Product pivot = products.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(products.get(j), pivot) <= 0) {
                i++;
                swap(products, i, j);
            }
        }

        swap(products, i + 1, high);
        return i + 1;
    }

    private void swap(List<Product> products, int i, int j) {
        Product temp = products.get(i);
        products.set(i, products.get(j));
        products.set(j, temp);
    }

    /**
     * MergeSort implementation
     * Time Complexity: O(n log n) guaranteed
     */
    private List<Product> mergeSort(List<Product> products, Comparator<Product> comparator) {
        if (products.size() <= 1) {
            return new ArrayList<>(products);
        }

        int mid = products.size() / 2;
        List<Product> left = mergeSort(products.subList(0, mid), comparator);
        List<Product> right = mergeSort(products.subList(mid, products.size()), comparator);

        return merge(left, right, comparator);
    }

    private List<Product> merge(List<Product> left, List<Product> right, Comparator<Product> comparator) {
        List<Product> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            result.add(left.get(i++));
        }

        while (j < right.size()) {
            result.add(right.get(j++));
        }

        return result;
    }

    /**
     * Get sorting algorithm performance info
     */
    public String getSortPerformanceInfo(String algorithm) {
        switch (algorithm.toLowerCase()) {
            case "quicksort":
                return "QuickSort: Average O(n log n), Worst O(n²) - Used for price and rating";
            case "mergesort":
                return "MergeSort: Guaranteed O(n log n) - Used for name sorting";
            case "binary":
                return "Binary Search: O(log n) - Requires sorted data";
            case "linear":
                return "Linear Search: O(n) - Used for text search";
            default:
                return "Unknown algorithm";
        }
    }
}
