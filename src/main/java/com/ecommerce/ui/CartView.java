package com.ecommerce.ui;

import com.ecommerce.models.CartItem;
import com.ecommerce.models.Product;
import com.ecommerce.services.CartService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Shopping cart view component
 */
public class CartView {
    private final CartService cartService;
    private final Consumer<Product> onItemRemoved;
    private final BiConsumer<Product, Integer> onQuantityChanged;

    private final VBox cartPanel;
    private final VBox itemsContainer;
    private final Label totalLabel;
    private final Label itemCountLabel;

    public CartView(CartService cartService,
                   Consumer<Product> onItemRemoved,
                   BiConsumer<Product, Integer> onQuantityChanged) {
        this.cartService = cartService;
        this.onItemRemoved = onItemRemoved;
        this.onQuantityChanged = onQuantityChanged;

        cartPanel = new VBox(10);
        cartPanel.setStyle("-fx-background-color: white; -fx-border-color: #ddd; " +
                          "-fx-border-width: 1; -fx-padding: 10;");

        // Header
        Label headerLabel = new Label("Shopping Cart");
        headerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        itemCountLabel = new Label("0 items");
        itemCountLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

        // Items container with scroll
        itemsContainer = new VBox(5);
        ScrollPane scrollPane = new ScrollPane(itemsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Total
        totalLabel = new Label("Total: $0.00");
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

        // Checkout button
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setMaxWidth(Double.MAX_VALUE);
        checkoutButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                               "-fx-font-size: 14px; -fx-padding: 10;");
        checkoutButton.setOnAction(e -> handleCheckout());

        // Clear cart button
        Button clearButton = new Button("Clear Cart");
        clearButton.setMaxWidth(Double.MAX_VALUE);
        clearButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        clearButton.setOnAction(e -> handleClearCart());

        cartPanel.getChildren().addAll(
            headerLabel,
            itemCountLabel,
            new Separator(),
            scrollPane,
            new Separator(),
            totalLabel,
            checkoutButton,
            clearButton
        );
    }

    /**
     * Refresh cart display
     */
    public void refresh() {
        itemsContainer.getChildren().clear();

        if (cartService.isEmpty()) {
            Label emptyLabel = new Label("Cart is empty");
            emptyLabel.setStyle("-fx-text-fill: #999; -fx-font-style: italic;");
            itemsContainer.getChildren().add(emptyLabel);
            itemCountLabel.setText("0 items");
            totalLabel.setText("Total: $0.00");
        } else {
            for (CartItem item : cartService.getItems()) {
                itemsContainer.getChildren().add(createCartItemView(item));
            }
            itemCountLabel.setText(cartService.getTotalItemCount() + " items");
            totalLabel.setText("Total: " + cartService.getFormattedTotal());
        }
    }

    /**
     * Create a cart item view
     */
    private VBox createCartItemView(CartItem item) {
        VBox itemBox = new VBox(5);
        itemBox.setPadding(new Insets(8));
        itemBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #eee; " +
                        "-fx-border-width: 1; -fx-border-radius: 3; -fx-background-radius: 3;");

        Product product = item.getProduct();

        // Product name
        Label nameLabel = new Label(product.getName());
        nameLabel.setWrapText(true);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

        // Price
        Label priceLabel = new Label(product.getFormattedPrice() + " each");
        priceLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #666;");

        // Quantity controls
        HBox quantityBox = new HBox(5);
        quantityBox.setAlignment(Pos.CENTER_LEFT);

        Button decreaseButton = new Button("-");
        decreaseButton.setStyle("-fx-font-size: 10px;");
        decreaseButton.setOnAction(e -> {
            if (item.getQuantity() > 1) {
                onQuantityChanged.accept(product, item.getQuantity() - 1);
            } else {
                onItemRemoved.accept(product);
            }
        });

        Label quantityLabel = new Label(String.valueOf(item.getQuantity()));
        quantityLabel.setStyle("-fx-font-weight: bold; -fx-min-width: 30; -fx-alignment: center;");

        Button increaseButton = new Button("+");
        increaseButton.setStyle("-fx-font-size: 10px;");
        increaseButton.setOnAction(e -> onQuantityChanged.accept(product, item.getQuantity() + 1));

        quantityBox.getChildren().addAll(
            new Label("Qty:"),
            decreaseButton,
            quantityLabel,
            increaseButton
        );

        // Subtotal and remove
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(bottomBox, Priority.ALWAYS);

        Label subtotalLabel = new Label(item.getFormattedSubtotal());
        subtotalLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2196F3;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button removeButton = new Button("×");
        removeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                            "-fx-font-size: 14px; -fx-font-weight: bold;");
        removeButton.setOnAction(e -> onItemRemoved.accept(product));

        bottomBox.getChildren().addAll(subtotalLabel, spacer, removeButton);

        itemBox.getChildren().addAll(nameLabel, priceLabel, quantityBox, bottomBox);
        return itemBox;
    }

    /**
     * Handle checkout
     */
    private void handleCheckout() {
        if (cartService.isEmpty()) {
            showAlert("Cart is empty", "Please add items to cart before checkout.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Checkout");
        alert.setHeaderText("Order Summary");
        alert.setContentText(
            "Items: " + cartService.getTotalItemCount() + "\n" +
            "Total: " + cartService.getFormattedTotal() + "\n\n" +
            "Thank you for your purchase!\n" +
            "(This is a demo - no actual payment processed)"
        );
        alert.showAndWait();

        cartService.clear();
        refresh();
    }

    /**
     * Handle clear cart
     */
    private void handleClearCart() {
        if (cartService.isEmpty()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Cart");
        alert.setHeaderText("Clear all items?");
        alert.setContentText("This will remove all items from your cart.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                cartService.clear();
                refresh();
            }
        });
    }

    /**
     * Show alert dialog
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getView() {
        return cartPanel;
    }
}
