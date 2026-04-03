package com.ecommerce.ui;

import com.ecommerce.models.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Product detail view component
 */
public class ProductDetailView {
    private final Product product;
    private final List<Product> recommendedProducts;
    private final Consumer<Product> onAddToCart;
    private final Consumer<Product> onProductClick;

    public ProductDetailView(Product product, List<Product> recommendedProducts,
                           Consumer<Product> onAddToCart, Consumer<Product> onProductClick) {
        this.product = product;
        this.recommendedProducts = recommendedProducts;
        this.onAddToCart = onAddToCart;
        this.onProductClick = onProductClick;
    }

    /**
     * Show product details in a modal dialog
     */
    public void show() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(product.getName());

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // Main content
        VBox mainContent = createMainContent();
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        layout.setCenter(scrollPane);

        // Bottom buttons
        HBox buttonBox = createButtonBox(dialog);
        layout.setBottom(buttonBox);

        Scene scene = new Scene(layout, 700, 600);
        dialog.setScene(scene);
        dialog.show();
    }

    /**
     * Create main content area
     */
    private VBox createMainContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(10));

        // Product header
        VBox header = new VBox(10);

        Label nameLabel = new Label(product.getName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label categoryLabel = new Label("Category: " + product.getCategory());
        categoryLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        HBox priceRatingBox = new HBox(20);
        Label priceLabel = new Label(product.getFormattedPrice());
        priceLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        Label ratingLabel = new Label("★ " + product.getRating() + " / 5.0");
        ratingLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #FFA000;");

        priceRatingBox.getChildren().addAll(priceLabel, ratingLabel);

        header.getChildren().addAll(nameLabel, categoryLabel, priceRatingBox);

        // Description
        VBox descriptionBox = new VBox(5);
        Label descriptionTitle = new Label("Description");
        descriptionTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label descriptionText = new Label(product.getDescription());
        descriptionText.setWrapText(true);
        descriptionText.setStyle("-fx-font-size: 14px;");

        descriptionBox.getChildren().addAll(descriptionTitle, descriptionText);

        // Specifications
        VBox specsBox = createSpecificationsBox();

        // Recommended products
        VBox recommendedBox = createRecommendedBox();

        content.getChildren().addAll(header, new Separator(), descriptionBox, new Separator(), specsBox);

        if (!recommendedProducts.isEmpty()) {
            content.getChildren().addAll(new Separator(), recommendedBox);
        }

        return content;
    }

    /**
     * Create specifications box
     */
    private VBox createSpecificationsBox() {
        VBox specsBox = new VBox(10);

        Label specsTitle = new Label("Specifications");
        specsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane specsGrid = new GridPane();
        specsGrid.setHgap(20);
        specsGrid.setVgap(8);

        Map<String, String> specs = product.getSpecifications();
        int row = 0;
        for (Map.Entry<String, String> entry : specs.entrySet()) {
            Label keyLabel = new Label(entry.getKey() + ":");
            keyLabel.setStyle("-fx-font-weight: bold;");

            Label valueLabel = new Label(entry.getValue());

            specsGrid.add(keyLabel, 0, row);
            specsGrid.add(valueLabel, 1, row);
            row++;
        }

        specsBox.getChildren().addAll(specsTitle, specsGrid);
        return specsBox;
    }

    /**
     * Create recommended products box
     */
    private VBox createRecommendedBox() {
        VBox recommendedBox = new VBox(10);

        Label recommendedTitle = new Label("Recommended Products (Priority Queue Ranking)");
        recommendedTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        FlowPane recommendedFlow = new FlowPane();
        recommendedFlow.setHgap(10);
        recommendedFlow.setVgap(10);

        for (Product recProduct : recommendedProducts) {
            VBox productCard = createRecommendedProductCard(recProduct);
            recommendedFlow.getChildren().add(productCard);
        }

        recommendedBox.getChildren().addAll(recommendedTitle, recommendedFlow);
        return recommendedBox;
    }

    /**
     * Create a small product card for recommended products
     */
    private VBox createRecommendedProductCard(Product recProduct) {
        VBox card = new VBox(5);
        card.setPrefSize(150, 120);
        card.setPadding(new Insets(8));
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #ddd; " +
                     "-fx-border-width: 1; -fx-border-radius: 3; -fx-background-radius: 3;");

        Label nameLabel = new Label(recProduct.getName());
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(140);
        nameLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");
        nameLabel.setAlignment(Pos.CENTER);

        Label priceLabel = new Label(recProduct.getFormattedPrice());
        priceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #2196F3;");

        Label ratingLabel = new Label("★ " + recProduct.getRating());
        ratingLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #FFA000;");

        Button viewButton = new Button("View");
        viewButton.setStyle("-fx-font-size: 9px;");
        viewButton.setOnAction(e -> onProductClick.accept(recProduct));

        // Hover effect removed to fix macOS compatibility issue

        card.getChildren().addAll(nameLabel, priceLabel, ratingLabel, viewButton);
        return card;
    }

    /**
     * Create button box at bottom
     */
    private HBox createButtonBox(Stage dialog) {
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        Spinner<Integer> quantitySpinner = new Spinner<>(1, 99, 1);
        quantitySpinner.setPrefWidth(80);

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addToCartButton.setOnAction(e -> {
            int quantity = quantitySpinner.getValue();
            for (int i = 0; i < quantity; i++) {
                onAddToCart.accept(product);
            }
            dialog.close();
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> dialog.close());

        buttonBox.getChildren().addAll(new Label("Quantity:"), quantitySpinner, addToCartButton, closeButton);
        return buttonBox;
    }
}
