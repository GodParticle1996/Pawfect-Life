package com.example.pawfectlife.service;

import com.example.pawfectlife.model.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {
    public boolean validateCart() {
        // Example implementation: Check if the cart is not empty
        return !cart.isEmpty();
    }

    private Map<Product, Integer> cart = new HashMap<>();

    public void addProduct(Product product) {
        // Find if a product with the same ID already exists in the cart
        Product existingProduct = cart.keySet().stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {
            // If the product exists, update its quantity
            int newQuantity = cart.get(existingProduct) + 1;
            cart.put(existingProduct, newQuantity);
        } else {
            // If the product doesn't exist, add it with quantity 1
            cart.put(product, 1);
        }
    }

    public void addProductWithQuantity(Product product, int quantity) {
        // Find if a product with the same ID already exists in the cart
        Product existingProduct = cart.keySet().stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {
            // If the product exists, update its quantity
            int newQuantity = cart.get(existingProduct) + quantity;
            cart.put(existingProduct, newQuantity);
        } else {
            // If the product doesn't exist, add it with the specified quantity
            cart.put(product, quantity);
        }
    }

    public void removeProduct(Product product) {
        // Find the product with the same ID
        Product existingProduct = cart.keySet().stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {
            cart.remove(existingProduct);
        }
    }

    public Map<Product, Integer> getProductsInCart() {
        return cart;
    }

    public double getTotal() {
        return cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public int getItemCount() {
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void clearCart() {
        cart.clear();
    }

    public void updateProductQuantity(Product product, int quantity) {
        // Find the product with the same ID
        Product existingProduct = cart.keySet().stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {
            if (quantity <= 0) {
                cart.remove(existingProduct);
            } else {
                cart.put(existingProduct, quantity);
            }
        }
    }
}