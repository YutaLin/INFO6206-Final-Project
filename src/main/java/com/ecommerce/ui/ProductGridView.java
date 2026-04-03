package com.ecommerce.ui;

import com.ecommerce.models.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Consumer;

/**
 * Product grid view component
 */
public class ProductGridView {
    private final FlowPane gridPane;
    private final ScrollPane scrollPane;
    private final Consumer<Product> onProductClick;
    private final Consumer<Product> onAddToCart;

    public ProductGridView(Consumer<Product> onProductClick, Consumer<Product> onAddToCart) {
        this.onProductClick = onProductClick;
        this.onAddToCart = onAddToCart;

        gridPane = new FlowPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(10));

        scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f5f5f5;");
    }

    /**
     * Set products to display
     */
    public void setProducts(List<Product> products) {
        gridPane.getChildren().clear();

        for (Product product : products) {
            gridPane.getChildren().add(createProductCard(product));
        }

        if (products.isEmpty()) {
            Label emptyLabel = new Label("No products found");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");
            gridPane.getChildren().add(emptyLabel);
        }
    }

    /**
     * Create a product card
     */
    private VBox createProductCard(Product product) {
        VBox card = new VBox(8);
        card.setPrefSize(200, 250);
        card.setMaxSize(200, 250);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: white; -fx-border-color: #ddd; " +
                     "-fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Product image placeholder
        StackPane imagePlaceholder = new StackPane();
        imagePlaceholder.setPrefSize(180, 120);
        imagePlaceholder.setStyle("-fx-background-color: #e0e0e0; -fx-border-radius: 3;");
        Label imageLabel = new Label("📦");
        imageLabel.setStyle("-fx-font-size: 48px;");
        imagePlaceholder.getChildren().add(imageLabel);

        // Product name
        Label nameLabel = new Label(product.getName());
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(180);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        nameLabel.setAlignment(Pos.CENTER);

        // Category
        Label categoryLabel = new Label(product.getCategory());
        categoryLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");

        // Price and rating
        HBox priceRatingBox = new HBox(10);
        priceRatingBox.setAlignment(Pos.CENTER);

        Label priceLabel = new Label(product.getFormattedPrice());
        priceLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        Label ratingLabel = new Label("★ " + product.getRating());
        ratingLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #FFA000;");

        priceRatingBox.getChildren().addAll(priceLabel, ratingLabel);

        // Buttons
        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.CENTER);

        Button viewButton = new Button("View");
        viewButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 10px;");
        viewButton.setOnAction(e -> onProductClick.accept(product));

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 10px;");
        addToCartButton.setOnAction(e -> onAddToCart.accept(product));

        buttonBox.getChildren().addAll(viewButton, addToCartButton);

        // Hover effect removed to fix macOS compatibility issue

        card.getChildren().addAll(
            imagePlaceholder,
            nameLabel,
            categoryLabel,
            priceRatingBox,
            buttonBox
        );

        return card;
    }

    public ScrollPane getView() {
        return scrollPane;
    }
}
