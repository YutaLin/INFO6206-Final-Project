package com.ecommerce.ui;

import com.ecommerce.models.Category;
import com.ecommerce.models.Product;
import com.ecommerce.services.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application window
 */
public class MainWindow extends Application {
    private ProductService productService;
    private CartService cartService;
    private SearchService searchService;
    private RecommendationService recommendationService;
    private BrowsingHistoryService browsingHistoryService;

    private ProductGridView productGridView;
    private CartView cartView;
    private BorderPane mainLayout;
    private TextField searchField;
    private ComboBox<String> categoryComboBox;
    private ComboBox<String> sortComboBox;
    private Label statusLabel;
    private Button backButton;
    private Button forwardButton;

    private List<Product> currentProducts;
    private String currentCategory = "All Products";

    @Override
    public void start(Stage primaryStage) {
        // Initialize services
        productService = new ProductService();
        cartService = new CartService();
        searchService = new SearchService();
        recommendationService = new RecommendationService();
        browsingHistoryService = new BrowsingHistoryService();

        currentProducts = new ArrayList<>(productService.getAllProducts());

        // Create main layout
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        // Create components
        mainLayout.setTop(createTopPanel());
        mainLayout.setCenter(createCenterPanel());
        mainLayout.setRight(createRightPanel());
        mainLayout.setBottom(createBottomPanel());

        // Create scene
        Scene scene = new Scene(mainLayout, 1200, 700);
        primaryStage.setTitle("Electronics E-Commerce Store - INFO 6205 Project");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial products
        updateProductGrid();
    }

    /**
     * Create top panel with search, category, and sort controls
     */
    private VBox createTopPanel() {
        VBox topPanel = new VBox(10);
        topPanel.setPadding(new Insets(0, 0, 10, 0));

        // Title
        Label titleLabel = new Label("Electronics E-Commerce Store");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Search bar
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        Label searchLabel = new Label("Search:");
        searchField = new TextField();
        searchField.setPromptText("Search products...");
        searchField.setPrefWidth(300);
        searchField.setOnAction(e -> performSearch());

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> performSearch());

        Button clearSearchButton = new Button("Clear");
        clearSearchButton.setOnAction(e -> clearSearch());

        searchBox.getChildren().addAll(searchLabel, searchField, searchButton, clearSearchButton);

        // Category and sort controls
        HBox controlBox = new HBox(20);
        controlBox.setAlignment(Pos.CENTER_LEFT);

        // Category dropdown
        Label categoryLabel = new Label("Category:");
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().add("All Products");
        populateCategoryDropdown(productService.getRootCategory(), "");
        categoryComboBox.setValue("All Products");
        categoryComboBox.setOnAction(e -> filterByCategory());

        // Sort dropdown
        Label sortLabel = new Label("Sort by:");
        sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll(
            "Name (A-Z)",
            "Name (Z-A)",
            "Price (Low to High)",
            "Price (High to Low)",
            "Rating (High to Low)",
            "Rating (Low to High)"
        );
        sortComboBox.setValue("Name (A-Z)");
        sortComboBox.setOnAction(e -> sortProducts());

        controlBox.getChildren().addAll(
            categoryLabel, categoryComboBox,
            sortLabel, sortComboBox
        );

        topPanel.getChildren().addAll(titleLabel, searchBox, controlBox);
        return topPanel;
    }

    /**
     * Create center panel with product grid
     */
    private VBox createCenterPanel() {
        VBox centerPanel = new VBox(10);

        // Navigation buttons (browsing history)
        HBox navBox = new HBox(10);
        navBox.setAlignment(Pos.CENTER_LEFT);

        backButton = new Button("← Back");
        backButton.setDisable(true);
        backButton.setOnAction(e -> navigateBack());

        forwardButton = new Button("Forward →");
        forwardButton.setDisable(true);
        forwardButton.setOnAction(e -> navigateForward());

        Label navLabel = new Label("Browsing History:");
        navBox.getChildren().addAll(navLabel, backButton, forwardButton);

        // Product grid
        productGridView = new ProductGridView(this::onProductClick, this::onAddToCart);

        centerPanel.getChildren().addAll(navBox, productGridView.getView());
        return centerPanel;
    }

    /**
     * Create right panel with cart
     */
    private VBox createRightPanel() {
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(0, 0, 0, 10));
        rightPanel.setPrefWidth(300);

        cartView = new CartView(cartService, this::onCartItemRemoved, this::onCartQuantityChanged);

        rightPanel.getChildren().add(cartView.getView());
        return rightPanel;
    }

    /**
     * Create bottom panel with status bar
     */
    private HBox createBottomPanel() {
        HBox bottomPanel = new HBox();
        bottomPanel.setPadding(new Insets(10, 0, 0, 0));

        statusLabel = new Label("Ready - " + currentProducts.size() + " products loaded");
        statusLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");

        bottomPanel.getChildren().add(statusLabel);
        return bottomPanel;
    }

    /**
     * Populate category dropdown recursively
     */
    private void populateCategoryDropdown(Category category, String prefix) {
        if (category == null) return;

        for (Category subcat : category.getSubcategories()) {
            categoryComboBox.getItems().add(prefix + subcat.getName());
            populateCategoryDropdown(subcat, prefix + "  ");
        }
    }

    /**
     * Perform search
     */
    private void performSearch() {
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            currentProducts = new ArrayList<>(productService.getAllProducts());
        } else {
            List<Product> allProducts = productService.getAllProducts();
            List<Product> nameResults = searchService.searchByName(allProducts, query);
            List<Product> specResults = searchService.searchBySpecs(allProducts, query);

            // Combine results (avoiding duplicates)
            currentProducts = new ArrayList<>(nameResults);
            for (Product p : specResults) {
                if (!currentProducts.contains(p)) {
                    currentProducts.add(p);
                }
            }
        }

        currentCategory = "Search Results";
        sortProducts();
        updateProductGrid();
        updateStatus("Found " + currentProducts.size() + " products matching '" + query + "'");
    }

    /**
     * Clear search
     */
    private void clearSearch() {
        searchField.clear();
        currentProducts = new ArrayList<>(productService.getAllProducts());
        currentCategory = "All Products";
        categoryComboBox.setValue("All Products");
        sortProducts();
        updateProductGrid();
        updateStatus("Showing all products");
    }

    /**
     * Filter products by category
     */
    private void filterByCategory() {
        String selectedCategory = categoryComboBox.getValue();

        if (selectedCategory.equals("All Products")) {
            currentProducts = new ArrayList<>(productService.getAllProducts());
            currentCategory = "All Products";
        } else {
            currentProducts = new ArrayList<>(
                productService.getProductsByCategory(selectedCategory.trim())
            );
            currentCategory = selectedCategory.trim();
        }

        sortProducts();
        updateProductGrid();
        updateStatus("Category: " + currentCategory + " (" + currentProducts.size() + " products)");
    }

    /**
     * Sort products based on selected criteria
     */
    private void sortProducts() {
        String sortOption = sortComboBox.getValue();

        switch (sortOption) {
            case "Name (A-Z)":
                searchService.sortByName(currentProducts, true);
                break;
            case "Name (Z-A)":
                searchService.sortByName(currentProducts, false);
                break;
            case "Price (Low to High)":
                searchService.sortByPrice(currentProducts, true);
                break;
            case "Price (High to Low)":
                searchService.sortByPrice(currentProducts, false);
                break;
            case "Rating (High to Low)":
                searchService.sortByRating(currentProducts, false);
                break;
            case "Rating (Low to High)":
                searchService.sortByRating(currentProducts, true);
                break;
        }
    }

    /**
     * Update product grid
     */
    private void updateProductGrid() {
        productGridView.setProducts(currentProducts);
    }

    /**
     * Handle product click - show details and add to browsing history
     */
    private void onProductClick(Product product) {
        // Add to browsing history (Stack operation)
        browsingHistoryService.visit(product);
        updateNavigationButtons();

        // Get recommendations (Priority Queue operation)
        List<Product> recommended = recommendationService.getRecommendedProducts(
            product, productService.getAllProducts(), 4
        );

        // Show product details
        ProductDetailView detailView = new ProductDetailView(
            product, recommended, this::onAddToCart, this::onProductClick
        );
        detailView.show();

        updateStatus("Viewing: " + product.getName() + " | History size: " +
                    browsingHistoryService.getBackHistorySize());
    }

    /**
     * Handle add to cart
     */
    private void onAddToCart(Product product) {
        cartService.addItem(product);
        cartView.refresh();
        updateStatus("Added " + product.getName() + " to cart");
    }

    /**
     * Handle cart item removed
     */
    private void onCartItemRemoved(Product product) {
        cartService.removeItem(product);
        cartView.refresh();
        updateStatus("Removed " + product.getName() + " from cart");
    }

    /**
     * Handle cart quantity changed
     */
    private void onCartQuantityChanged(Product product, int newQuantity) {
        cartService.updateQuantity(product.getId(), newQuantity);
        cartView.refresh();
        updateStatus("Updated quantity for " + product.getName());
    }

    /**
     * Navigate back in browsing history
     */
    private void navigateBack() {
        Product previousProduct = browsingHistoryService.back();
        if (previousProduct != null) {
            onProductClick(previousProduct);
            updateStatus("Navigated back to: " + previousProduct.getName());
        }
        updateNavigationButtons();
    }

    /**
     * Navigate forward in browsing history
     */
    private void navigateForward() {
        Product nextProduct = browsingHistoryService.forward();
        if (nextProduct != null) {
            onProductClick(nextProduct);
            updateStatus("Navigated forward to: " + nextProduct.getName());
        }
        updateNavigationButtons();
    }

    /**
     * Update navigation button states
     */
    private void updateNavigationButtons() {
        backButton.setDisable(!browsingHistoryService.canGoBack());
        forwardButton.setDisable(!browsingHistoryService.canGoForward());
    }

    /**
     * Update status label
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
