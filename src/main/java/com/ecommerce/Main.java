package com.ecommerce;

import com.ecommerce.ui.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main entry point for the E-Commerce Electronics Store application
 * Demonstrates use of custom data structures in a complete JavaFX application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            MainWindow mainWindow = new MainWindow();
            mainWindow.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Print application info
        System.out.println("=".repeat(60));
        System.out.println("E-Commerce Electronics Store");
        System.out.println("INFO 6205 - Data Structures Project");
        System.out.println("=".repeat(60));
        System.out.println("\nData Structures Implemented:");
        System.out.println("1. Binary Search Tree - Product catalog storage");
        System.out.println("2. Hash Table - Fast product lookup");
        System.out.println("3. Stack - Browsing history (back/forward)");
        System.out.println("4. Priority Queue - Product recommendations");
        System.out.println("5. Linked List - Shopping cart");
        System.out.println("6. Category Tree - Recursive category traversal");
        System.out.println("\nAlgorithms Demonstrated:");
        System.out.println("1. QuickSort - Price/Rating sorting (O(n log n))");
        System.out.println("2. MergeSort - Name sorting (O(n log n))");
        System.out.println("3. Binary Search - Fast ID lookup (O(log n))");
        System.out.println("4. Recursion - Category tree traversal");
        System.out.println("=".repeat(60));
        System.out.println("\nLaunching application...\n");

        launch(args);
    }
}
